package org.mixitconf.api.dto

import com.squareup.moshi.Json
import org.mixitconf.model.Talk
import org.mixitconf.model.enums.Language
import org.mixitconf.model.enums.Room
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.model.enums.Topic
import java.time.Instant
import java.util.*

data class TalkApiDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "format")
    val format: TalkFormat,
    @field:Json(name = "event")
    val event: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "summary")
    val summary: String?,
    @field:Json(name = "speakerIds")
    val speakerIds: List<String> = emptyList(),
    @field:Json(name = "language")
    val language: Language = Language.FRENCH,
    @field:Json(name = "addedAt")
    val addedAt: String,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "topic")
    val topic: String?,
    @field:Json(name = "room")
    val room: String?,
    @field:Json(name = "start")
    val start: String?,
    @field:Json(name = "end")
    val end: String?
){
    fun toEntity() = Talk(
        id,
        format,
        event,
        title,
        summary ?: "",
        speakerIds.joinToString(","),
        language,
        description,
        Topic.of(topic),
        Room.of(room),
        Date(Instant.parse("${start?.replace("/", "-")}.000Z").toEpochMilli()),
        Date(Instant.parse("${end?.replace("/", "-")}.000Z").toEpochMilli())
    )
}





