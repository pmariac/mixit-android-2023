/*
 * Copyright (c) 2020. Fairphone B.V.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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