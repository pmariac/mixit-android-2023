package org.mixitconf.ui

import org.koin.dsl.module
import org.mixitconf.ui.login.LoginViewModel

/**
 * Dependency module for the ui module.
 */
val uiModule = module {

    /**
     * Declare a single instance of [LoginViewModel].
     */
    single {
        LoginViewModel(get())
    }
}

