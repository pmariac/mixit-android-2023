package org.mixitconf.ui.speaker

import android.view.View
import org.mixitconf.R
import org.mixitconf.databinding.ItemListSpeakerBinding
import org.mixitconf.model.Speaker
import org.mixitconf.setSpeakerImage
import org.mixitconf.ui.SimpleItemViewHolder
import org.mixitconf.ui.SimpleListAdapter

/**
 * Adapter used to display [Speaker]s.
 */
class SpeakersAdapter : SimpleListAdapter<Speaker, SpeakersAdapter.SpeakerViewHolder>() {

    override val itemViewLayoutId: Int by lazy { R.layout.item_list_speaker }

    override fun onCreateViewHolder(itemView: View, viewType: Int) = SpeakerViewHolder(itemView)

    inner class SpeakerViewHolder(override val containerView: View) : SimpleItemViewHolder<Speaker>(containerView) {
        var viewBinding: ItemListSpeakerBinding = ItemListSpeakerBinding.bind(containerView)

        override fun bindItem(item: Speaker) {
            viewBinding.apply {
                speakerName.text = item.fullname
                speakerBio.text = item.descriptionFr
                speakerImage.setSpeakerImage(item)
            }
        }

        override fun resetViews() {
        }
    }
}