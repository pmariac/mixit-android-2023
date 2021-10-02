package org.mixitconf.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.mixitconf.database.adapter.DateConverter
import org.mixitconf.database.adapter.EnumConverters
import org.mixitconf.model.Link
import org.mixitconf.model.User
import org.mixitconf.model.dao.LinkRepository
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk


@Database(entities = [User::class, Link::class, Talk::class, Speaker::class], version = 1)
@TypeConverters(DateConverter::class, EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userRepository(): UserRepository
    abstract fun speakerRepository(): SpeakerRepository
    abstract fun talkRepository(): TalkRepository
    abstract fun linkRepository(): LinkRepository
}