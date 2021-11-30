package org.mixitconf.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mixitconf.ui.login.LoginViewModel
import org.mixitconf.ui.settings.FavoritesViewModel
import org.mixitconf.ui.speaker.SpeakersViewModel
import org.mixitconf.ui.talk.TalksViewModel

/**
 * Dependency module for the ui module.
 */
val uiModule = module {

    /**
     * Declare a single instance of [LoginViewModel].
     */
    viewModel {
        LoginViewModel(get())
    }

    /**
     * Declare a single instance of [TalksViewModel].
     */
    viewModel {
        TalksViewModel(get(), get(), get())
    }

    /**
     * Declare a single instance of [SpeakersViewModel].
     */
    viewModel {
        SpeakersViewModel(get(), get())
    }

    /**
     * Declare a single instance of [FavoritesViewModel].
     */
    viewModel {
        FavoritesViewModel(get(), get())
    }
}

