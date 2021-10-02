package org.mixitconf.ui.talk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mixitconf.MainNavigationGraphDirections
import org.mixitconf.databinding.FragmentTalksBinding
import org.mixitconf.model.entity.Talk
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.ui.BaseFragment

class TalksFragment : BaseFragment<FragmentTalksBinding>() {

    private val viewModel: TalksViewModel by sharedViewModel()

    private val talksAdpater = TalksAdapter().apply {
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
        viewBinding.rvTalks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = talksAdpater
        }
    }

    private fun search() {
        viewModel.search().observe(viewLifecycleOwner, { talks ->
            talksAdpater.setItems(talks)
        })
    }

    private fun openTalk(talk: Talk) {
        if (talk.format.isTalk) {
            val directions = MainNavigationGraphDirections.actionTalksFragmentToTalkDetailFragment(talk.id)
            findNavController().navigate(directions)
        }
    }
}