package org.mixitconf.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mixitconf.model.enums.LinkType
import org.mixitconf.model.enums.SocialType
import java.util.*

@Entity
data class Link(
    val name: String,
    val url: String,
    val speakerId: String,
    val linkType: LinkType,
    @PrimaryKey val id: String = UUID.randomUUID().toString()
) {
    val socialType
        get() = SocialType.values().first { social -> url.contains(social.pattern) }
}

