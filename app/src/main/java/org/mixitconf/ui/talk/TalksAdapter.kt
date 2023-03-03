package org.mixitconf.ui.talk

import android.view.View
import org.mixitconf.R
import org.mixitconf.databinding.ItemListTalkBinding
import org.mixitconf.model.Talk
import org.mixitconf.model.enums.Language
import org.mixitconf.model.enums.TalkFormat.*
import org.mixitconf.ui.SimpleItemViewHolder
import org.mixitconf.ui.SimpleListAdapter

/**
 * Adapter used to display [Talk]s.
 */
class TalksAdapter : SimpleListAdapter<Talk, TalksAdapter.TalkViewHolder>() {

    override val itemViewLayoutId: Int by lazy { R.layout.item_list_talk }

    override fun onCreateViewHolder(itemView: View, viewType: Int) = TalkViewHolder(itemView)

    inner class TalkViewHolder(override val containerView: View) : SimpleItemViewHolder<Talk>(containerView) {
        var viewBinding: ItemListTalkBinding = ItemListTalkBinding.bind(containerView)

        override fun bindItem(item: Talk) {
            viewBinding.apply {
                tvTalkItemName.text = if (item.format == PLANNING_DAY) item.title.uppercase() else item.title
                tvTalkItemTime.text = item.getTimeLabel(context.resources)

                when (item.format) {
                    TALK, WORKSHOP, KEYNOTE_SURPRISE, CLOSING_SESSION, RANDOM, LIGHTNING_TALK -> {
                        setTalkDetail(item)
                        setColors(item.getBackgroundColor(R.color.white))
                    }
                    KEYNOTE -> {
                        setTalkDetail(item)
                        //setColors(item.getBackgroundColor(R.color.colorSecondary))
                        setColors(R.color.colorPrimary, nameColor = android.R.color.white, descColor = android.R.color.white)
                    }
                    PLANNING_DAY -> {
                        setNonTalkDetail(showTime = false)
                        setColors(item.getBackgroundColor(R.color.colorSecondary))
                        //setColors(R.color.colorPrimary, nameColor = android.R.color.white)
                    }
                    PLANNING_PARTY -> {
                        setNonTalkDetail()
                        setColors(item.getBackgroundColor(R.color.colorAccent), timeColor = android.R.color.white)
                    }
                    PLANNING_INTRODUCTION_SESSION, PLANNING_LUNCH, PLANNING_INTRODUCTION_MIXETTE, PLANNING_LUNCH2, PLANNING_ORGA_SPEECH, PLANNING_WELCOME -> {
                        setNonTalkDetail()
                        setColors(item.getBackgroundColor(R.color.colorShadow))
                    }
                    PLANNING_PAUSE_10_MIN, PLANNING_PAUSE_25_MIN, PLANNING_PAUSE_30_MIN, PLANNING_PAUSE_20_MIN -> {
                        setNonTalkDetail()
                        setColors(item.getBackgroundColor(R.color.colorShadow))
                    }

                }
            }
        }

        override fun resetViews() {
            viewBinding.apply {
                tvTalkItemDescription.visibility = View.GONE
                tvTalkItemRoom.visibility = View.GONE
                tvTalkItemType.visibility = View.GONE
                tvTalkItemLanguage.visibility = View.GONE
                imgTalkItem.visibility = View.VISIBLE
                imgTalkItemNotFavorite.visibility = View.GONE
                imgTalkItemFavorite.visibility = View.GONE
                tvTalkItemTime.visibility = View.GONE
            }
        }

        private fun setColors(background: Int, nameColor: Int = R.color.black, timeColor: Int = R.color.textShadow, descColor: Int = R.color.textShadow) {
            containerView.setBackgroundColor(context.getColor(background))
            viewBinding.apply {
                tvTalkItemName.setTextColor(context.getColor(nameColor))
                tvTalkItemTime.setTextColor(context.getColor(timeColor))
                tvTalkItemDescription.setTextColor(context.getColor(descColor))
            }
        }

        private fun setTalkDetail(talk: Talk) {
            viewBinding.apply {
                imgTalkItem.setImageResource(talk.topicDrawableResource)
                tvTalkItemName.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                tvTalkItemTime.visibility = View.VISIBLE

                tvTalkItemType.setText(talk.format.label)
                tvTalkItemType.visibility = View.VISIBLE

                tvTalkItemDescription.text = talk.summary
                tvTalkItemDescription.visibility = View.VISIBLE

                tvTalkItemRoom.text = context.getText(talk.room.i18nId)
                tvTalkItemRoom.visibility = View.VISIBLE

                tvTalkItemLanguage.visibility = if (talk.language == Language.ENGLISH) View.VISIBLE else View.GONE
                imgTalkItemNotFavorite.visibility = if (talk.favorite) View.GONE else View.VISIBLE
                imgTalkItemFavorite.visibility = if (talk.favorite) View.VISIBLE else View.GONE
            }
        }

        private fun setNonTalkDetail(showTime: Boolean = true) {
            viewBinding.apply {
                if (showTime) {
                    imgTalkItem.setImageDrawable(null)
                } else {
                    imgTalkItem.visibility = View.GONE
                }
                tvTalkItemTime.visibility = if (showTime) View.VISIBLE else View.GONE
            }
        }
    }


}