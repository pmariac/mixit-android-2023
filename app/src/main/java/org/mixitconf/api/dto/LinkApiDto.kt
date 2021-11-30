package org.mixitconf.api.dto

import com.squareup.moshi.Json
import org.mixitconf.model.Link
import org.mixitconf.model.enums.LinkType
import org.mixitconf.model.enums.SocialType


data class LinkApiDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "url")
    val url: String
) {
    fun toEntity(userId: String): Link {
        val social = SocialType.values().firstOrNull { url.contains(it.pattern) }
        return Link(name, url, userId, if (social != null) LinkType.Social else LinkType.Web)
    }
}

