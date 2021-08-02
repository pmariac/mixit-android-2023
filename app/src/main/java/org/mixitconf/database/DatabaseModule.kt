package org.mixitconf.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.*

/**
 * Dependency module for the data module.
 */
val databaseModule = module {

    /**
     * Declare a single instance of [UserDao].
     */
    single { get<AppDatabase>().userDao() }

    /**
     * Declare a single instance of [AppDatabase].
     */
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "mixit-database")
            .build()
    }
}

