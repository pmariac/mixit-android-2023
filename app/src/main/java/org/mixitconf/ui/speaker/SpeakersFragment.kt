package org.mixitconf.ui.speaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mixitconf.MainNavigationGraphDirections
import org.mixitconf.databinding.FragmentSpeakersBinding
import org.mixitconf.model.Speaker
import org.mixitconf.ui.BaseFragment

class SpeakersFragment : BaseFragment<FragmentSpeakersBinding>() {

    private val viewModel: SpeakersViewModel by sharedViewModel()

    private val speakersAdapter = SpeakersAdapter().apply {
        onItemClickListener = { speaker, _ -> openSpeaker(speaker) }
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentSpeakersBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        search()
    }

    private fun initRecyclerView() {
        viewBinding.rvSpeakers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = speakersAdapter
        }
    }

    private fun search() {
        viewModel.search().observe(viewLifecycleOwner, { speakers -> speakersAdapter.setItems(speakers) })
    }

    private fun openSpeaker(speaker: Speaker) {
        val directions = MainNavigationGraphDirections.actionSpeakersFragmentToSpeakerDetailFragment(speaker.login)
        findNavController().navigate(directions)
    }
}