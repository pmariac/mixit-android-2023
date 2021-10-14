package org.mixitconf.ui.talk

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
import org.mixitconf.R
import org.mixitconf.databinding.FragmentTalkDetailBinding
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk
import org.mixitconf.model.enums.Language
import org.mixitconf.model.enums.Room
import org.mixitconf.model.enums.Topic
import org.mixitconf.ui.BaseFragment
import org.mixitconf.ui.speaker.SpeakersAdapter

class TalkDetailFragment : BaseFragment<FragmentTalkDetailBinding>() {

    private val args by navArgs<TalkDetailFragmentArgs>()

    private val talkId: String by lazy { args.talkId }

    private val viewModel: TalksViewModel by sharedViewModel()

    private val speakersAdapter = SpeakersAdapter().apply {
        onItemClickListener = { speaker, _ -> openSpeaker(speaker) }
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentTalkDetailBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOne(talkId).observe(viewLifecycleOwner, { talk ->
            initTalk(talk)
            if (talk != null) {
                viewModel.getTalkSpeakers(talk.speakerIdList).observe(viewLifecycleOwner, {
                    speakersAdapter.setItems(it!!)
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.btnFavorite.setOnClickListener(null)
    }

    private fun initTalk(talk: Talk?): Unit {
        if (talk != null) {
            viewBinding.apply {
                fragmentTalkDetailHeader.apply {
                    tvTalkName.text = talk.title
                    tvTalkTime.text = talk.getTimeLabel(resources)
                    tvTalkRoom.text = (talk.room ?: Room.UNKNOWN).let {
                        getString(
                            R.string.talk_room_detail, getString(it.i18nId), it.capacity.toString()
                        )
                    }
                    tvMarkedAsFavorite.visibility = if (talk.favorite) View.VISIBLE else View.GONE
                }

                fragmentTalkDetailContent.apply {
                    tvTalkSummary.text = talk.summaryInMarkdown
                    tvTalkSummary.visibility = if (talk.summary.isEmpty()) View.GONE else View.VISIBLE
                    tvTalkDescription.text = talk.descriptionInMarkdown
                    tvTalkDescription.visibility = if (talk.description.isNullOrEmpty()) View.GONE else View.VISIBLE
                    tvTalkTopic.text = talk.topic.name
                    tvTalkLanguage.text = getText(
                        if (talk.language == Language.ENGLISH) R.string.talk_language_en else R.string.talk_language_fr
                    )
                    imgTalkTopic.setImageResource(talk.topicDrawableResource)

                    rvTalkSpeakers.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = speakersAdapter
                    }
                }

                btnFavorite.apply {
                    setImageResource(if (talk.favorite) R.drawable.mxt_icon_favorite else R.drawable.mxt_icon_non_favorite)
                    setOnClickListener { updateFavorite(talk) }
                }
            }
        }
    }

    private fun updateFavorite(talk: Talk) {
        viewModel.update(talk.copy(favorite = !talk.favorite)).observe(viewLifecycleOwner, { initTalk(it) })
    }

    private fun openSpeaker(speaker: Speaker) {
        val directions = MainNavigationGraphDirections.actionSpeakersFragmentToSpeakerDetailFragment(speaker.login)
        findNavController().navigate(directions)
    }
}