package org.mixitconf

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.mixitconf.api.apiModule
import org.mixitconf.database.databaseModule
import org.mixitconf.service.notification.NotificationChannelManager
import org.mixitconf.service.serviceModule
import org.mixitconf.ui.uiModule
import timber.log.Timber

/**
 * Main [Application] class.
 */
class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    private val modules: List<Module>
        get() = listOf(
            apiModule,
            databaseModule,
            serviceModule,
            uiModule
        )

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        initTimber()
        createNotificationChannels()
        instance = this
    }

    private fun createNotificationChannels() {
        NotificationChannelManager.createPlanningNotificationChannel(this)
        NotificationChannelManager.createSyncNotificationChannel(this)
    }

    private fun injectDependencies() {
        startKoin {
            modules(modules)
            androidContext(this@App)
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}