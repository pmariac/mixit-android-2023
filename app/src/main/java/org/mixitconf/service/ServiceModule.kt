package org.mixitconf.service

import android.accounts.AccountManager
import androidx.preference.PreferenceManager
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.mixitconf.service.security.LoginService
import java.util.*

/**
 * Dependency module for the data module.
 */
val serviceModule = module {
    /**
     * Declare a single instance of [AccountManager].
     */
    single { AccountManager.get(androidContext()) }

    /**
     * Declare a single instance of [LoginService].
     */
    single { LoginService(get(), get(), get()) }

    /**
     * Declare a single instance of [PreferenceManager].
     */
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}

