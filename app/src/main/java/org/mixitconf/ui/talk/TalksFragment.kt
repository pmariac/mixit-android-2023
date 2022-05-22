package org.mixitconf.ui.talk

import android.os.Bundle
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

open class TalksFragment : BaseFragment<FragmentTalksBinding>() {

    protected val viewModel: TalksViewModel by sharedViewModel()

    protected val talksAdpater = TalksAdapter().apply {
        onItemClickListener = { talk, _ -> openTalk(talk) }
        stateRestorationPolicy = PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentTalksBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
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
        }
    }

    private fun openTalk(talk: Talk) {
        if (talk.format.isTalk) {
            val directions = MainNavigationGraphDirections.actionTalksFragmentToTalkDetailFragment(talk.id!!)
            findNavController().navigate(directions)
        }
    }
}