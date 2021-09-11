/*
 * Copyright 2019 Fairphone B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mixitconf.service

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


object AppPreferences : KoinComponent {

    private val context: Application by inject()
    val prefs: SharedPreferences by inject()

    const val KEY_SYNC_FAVORITE = "sync_favorites"
    const val KEY_DATA = "sync_data"

    var mayNotifyBeforeTalkStart: Boolean
        get() = getBoolean(Keys.KEY_TALK_NOTIFICATION, true)
        set(value) = putBoolean(Keys.KEY_TALK_NOTIFICATION, value)

    var maySyncDataInBackground: Boolean
        get() = getBoolean(Keys.KEY_DATA, true)
        set(value) = putBoolean(Keys.KEY_DATA, value)

    var maySyncFavoriteInBackground: Boolean
        get() = getBoolean(Keys.KEY_SYNC_FAVORITE, true)
        set(value) = putBoolean(Keys.KEY_SYNC_FAVORITE, value)

    private fun getBoolean(key: String, default: Boolean) =
        prefs.getBoolean(key, default)

    private fun putBoolean(key: String, value: Boolean) =
        prefs.edit { putBoolean(key, value) }

    object Keys {
        const val KEY_TALK_NOTIFICATION = "talk_notification"
        const val KEY_SYNC_FAVORITE = "sync_favorites"
        const val KEY_DATA = "sync_data"
    }
}
