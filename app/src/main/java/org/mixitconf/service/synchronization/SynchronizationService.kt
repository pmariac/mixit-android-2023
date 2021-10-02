package org.mixitconf.service.synchronization

import android.app.Application
import androidx.room.Transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mixitconf.service.AppPreferences
import retrofit2.Response
import timber.log.Timber

abstract class SynchronizationService<Dto> : KoinComponent {

    protected val context: Application by inject()

    companion object {
        enum class SyncMode { MANUAL, BACKGROUND }
        enum class Result { Success, Error }
    }

    @Transaction
    inline suspend fun <reified Entity> synchronize(mode: SyncMode) {
        // We need to check user preference to know if he wants to do this job in background
        if (mode == SyncMode.BACKGROUND && !AppPreferences.maySyncDataInBackground) {
            Timber.d("User choose to disable background data sync")
            return
        }
        runCatching { read() }
            .onFailure {
                it.printStackTrace()
                Timber.e(it)
                sendNotification<Entity>(Result.Error)
            }
            .onSuccess { save(it.body() ?: emptyList(), mode) }
    }

    abstract suspend fun read(): Response<List<Dto>>

    abstract suspend fun save(result: List<Dto>, mode: SyncMode)

    inline fun <reified Entity> sendNotification(result: Result) {
        if (result == Result.Success) {
            Timber.i("{Entity::class.java} date are synchronised")
        } else {
            Timber.e("Impossible to synchronize ${Entity::class.java}")
        }
    }
}