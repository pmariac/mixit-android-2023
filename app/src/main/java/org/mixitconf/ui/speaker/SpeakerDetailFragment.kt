package org.mixitconf.ui.speaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mixitconf.MainNavigationGraphDirections
import org.mixitconf.databinding.FragmentSpeakerDetailBinding
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk
import org.mixitconf.setSpeakerImage
import org.mixitconf.ui.BaseFragment
import org.mixitconf.ui.talk.TalksAdapter

class SpeakerDetailFragment : BaseFragment<FragmentSpeakerDetailBinding>() {

    private val args by navArgs<SpeakerDetailFragmentArgs>()

    private val login: String by lazy { args.login }

    private val viewModel: SpeakersViewModel by sharedViewModel()

    private val talksAdapter = TalksAdapter().apply {
        onItemClickListener = { talk, _ -> openTalk(talk) }
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentSpeakerDetailBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOne(login).observe(viewLifecycleOwner, { speaker ->
            initSpeaker(speaker)
            if (speaker != null) {
                viewModel.getSpeakerTalks(login).observe(viewLifecycleOwner, {
                    talksAdapter.setItems(it!!)
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.btnFavorite.setOnClickListener(null)
    }

    private fun initSpeaker(speaker: Speaker?) {
        if (speaker != null) {
            viewBinding.apply {
                fragmentSpeakerDetailContent.apply {
                    tvSpeakerName.text = speaker.fullname
                    tvSpeakerSummary.text = speaker.descriptionInMarkdown
                    imgSpeaker.setSpeakerImage(speaker)
                    rvTalks.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = talksAdapter
                    }

                }
            }
        }
    }

    private fun openTalk(talk: Talk) {
        val directions = MainNavigationGraphDirections.actionTalksFragmentToTalkDetailFragment(talk.id!!)
        findNavController().navigate(directions)
    }
}