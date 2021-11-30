package org.mixitconf.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Dependency module for the data module.
 */
val databaseModule = module {

    /**
     * Declare a single instance of [UserRepository].
     */
    single { get<AppDatabase>().userRepository() }

    /**
     * Declare a single instance of [LinkRepository].
     */
    single { get<AppDatabase>().linkRepository() }

    /**
     * Declare a single instance of [SpeakerRepository].
     */
    single { get<AppDatabase>().speakerRepository() }

    /**
     * Declare a single instance of [TalkRepository].
     */
    single { get<AppDatabase>().talkRepository() }

    /**
     * Declare a single instance of [AppDatabase].
     */
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "mixit-database")
            .build()
    }
}

