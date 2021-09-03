package org.mixitconf.api.dto

import com.squareup.moshi.Json

class FavoriteDto(
    @field:Json(name = "talkId")
    val talkId: String,
    @field:Json(name = "selected")
    val selected: Boolean
)