package org.mixitconf.database.adapter

import androidx.room.TypeConverter
import org.mixitconf.model.enums.*

class EnumConverters {

    @TypeConverter
    fun toLanguage(value: String?): Language? {
        return value?.let { Language.valueOf(it) }
    }

    @TypeConverter
    fun fromLanguage(value: Language?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLinkType(value: String?): LinkType? {
        return value?.let { LinkType.valueOf(it) }
    }

    @TypeConverter
    fun fromLinkType(value: LinkType?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toTalkFormat(value: String?): TalkFormat? {
        return value?.let { TalkFormat.valueOf(it) }
    }

    @TypeConverter
    fun fromTalkFormat(value: TalkFormat?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toRoom(value: String?): Room? {
        return value?.let { Room.valueOf(it) }
    }

    @TypeConverter
    fun fromRoom(value: Room?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toTopic(value: String?): Topic? {
        return value?.let { Topic.valueOf(it) }
    }

    @TypeConverter
    fun fromTopic(value: Topic?): String? {
        return value?.toString()
    }
}