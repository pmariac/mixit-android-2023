package org.mixitconf.model.enums

import org.mixitconf.R

enum class Topic(val drawableResourse: Int) {
    aliens(R.drawable.mxt_topic_alien),
    design(R.drawable.mxt_topic_design),
    hacktivism(R.drawable.mxt_topic_hacktivism),
    learn(R.drawable.mxt_topic_learn),
    makers(R.drawable.mxt_topic_maker),
    team(R.drawable.mxt_topic_team),
    tech(R.drawable.mxt_topic_tech),
    ethics(R.drawable.mxt_topic_ethics),
    lifestyle(R.drawable.mxt_topic_lifestyle),
    unknown(R.drawable.mxt_topic_design);

    companion object {
        fun of(string: String?): Topic =
            values().firstOrNull { it.name == string } ?: unknown
    }
}