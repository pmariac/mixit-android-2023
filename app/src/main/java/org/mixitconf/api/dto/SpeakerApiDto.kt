package org.mixitconf.service.synchronization.dto

import com.squareup.moshi.Json
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.enums.Language


data class SpeakerApiDto(
    @field:Json(name = "login")
    val login: String,
    @field:Json(name = "firstname")
    val firstname: String?,
    @field:Json(name = "lastname")
    val lastname: String?,
    @field:Json(name = "company")
    val company: String?,
    @field:Json(name = "photoUrl")
    val photoUrl: String?,
    @field:Json(name = "description")
    val description: Map<Language, String> = emptyMap(),
    @field:Json(name = "links")
    val links: List<LinkApiDto> = emptyList()
) {
    fun toEntity() = Speaker(
        login,
        firstname ?: "",
        lastname ?: "",
        company,
        description.get(Language.FRENCH),
        description.get(Language.ENGLISH)
    )
}
