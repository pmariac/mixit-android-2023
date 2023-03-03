package org.mixitconf.ui.talk

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mixitconf.MainNavigationGraphDirections
import org.mixitconf.databinding.FragmentTalksBinding
import org.mixitconf.model.Talk
import org.mixitconf.ui.BaseFragment
import java.util.Date

open class TalksFragment : BaseFragment<FragmentTalksBinding>() {

    private var listState: Parcelable? = null

    protected val viewModel: TalksViewModel by sharedViewModel()

    protected val talksAdpater = TalksAdapter().apply {
        onItemClickListener = { talk, _ -> openTalk(talk) }
        stateRestorationPolicy = PREVENT_WHEN_EMPTY
    }

    companion object {
        const val STATE = "org.mixitconf.ui.talk"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = viewBinding.rvTalks.layoutManager?.onSaveInstanceState()
        outState.putParcelable(STATE, listState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE)) {
           listState = savedInstanceState.getParcelable(STATE)
        }
        return FragmentTalksBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        search()
    }

    private fun initRecyclerView() {
        viewBinding.txtNoFavorite.visibility = View.INVISIBLE
        viewBinding.rvTalks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = talksAdpater
        }
    }

    protected open fun search() {
        viewModel.search().observe(viewLifecycleOwner) { talks ->
            talksAdpater.setItems(talks)
            if (listState == null) {
                val index = talks.indexOfFirst { talk -> Date().let { talk.startTime > it } }
                viewBinding.rvTalks.scrollToPosition(index)
            }
        }
    }

    private fun openTalk(talk: Talk) {
        if (talk.format.isTalk) {
            val directions = MainNavigationGraphDirections.actionTalksFragmentToTalkDetailFragment(talk.id!!)
            findNavController().navigate(directions)
        }
    }
}