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
