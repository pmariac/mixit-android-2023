package org.mixitconf.ui.speaker

import android.view.View
import org.mixitconf.R
import org.mixitconf.databinding.ItemListSpeakerBinding
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.enums.TalkFormat.*
import org.mixitconf.setSpeakerImage
import org.mixitconf.ui.SimpleItemViewHolder
import org.mixitconf.ui.SimpleListAdapter

/**
 * Adapter used to display [Article]s.
 */
class SpeakersAdapter : SimpleListAdapter<Speaker, SpeakersAdapter.SpeakerViewHolder>() {

    override val itemViewLayoutId: Int by lazy { R.layout.item_list_speaker }

    override fun onCreateViewHolder(itemView: View, viewType: Int) = SpeakerViewHolder(itemView)

    inner class SpeakerViewHolder(override val containerView: View) : SimpleItemViewHolder<Speaker>(containerView) {
        var viewBinding: ItemListSpeakerBinding = ItemListSpeakerBinding.bind(containerView)

        override fun bindItem(speaker: Speaker) {
            viewBinding.apply {
                speakerName.text = speaker.fullname
                speakerBio.text = speaker.descriptionFr
                speakerImage.setSpeakerImage(speaker)
            }
        }

        override fun resetViews() {
        }
    }
}