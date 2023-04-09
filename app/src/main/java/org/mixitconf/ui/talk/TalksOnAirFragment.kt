package org.mixitconf.ui.talk

import org.mixitconf.model.enums.TalkFormat
import java.util.*

open class TalksOnAirFragment : TalksFragment() {
    override fun search() {
        viewModel.search().observe(viewLifecycleOwner) { talks ->
            talksAdpater.setItems(talks.filter { listOf(TalkFormat.ON_AIR, TalkFormat.INTERVIEW).contains(it.format) })
            if (listState == null) {
                val index = talks.indexOfFirst { talk -> Date().let { talk.startTime > it } }
                viewBinding.rvTalks.scrollToPosition(index)
            }
        }
    }


}