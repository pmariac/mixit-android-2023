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
        enum class Result(val result: Boolean) { Success(true), Error(false), Nothing(true)}
    }

    @Transaction
    suspend inline fun <reified Entity> synchronize(mode: SyncMode): Result {
        // We need to check user preference to know if he wants to do this job in background
        if (mode == SyncMode.BACKGROUND && !AppPreferences.maySyncDataInBackground) {
            Timber.d("User choose to disable background data sync")
            return Result.Nothing
        }
        return try {
            save(read().body() ?: emptyList(), mode).let { Result.Success }
        } catch (e: Exception) {
            Result.Error
        }
    }

    abstract suspend fun read(): Response<List<Dto>>

    abstract suspend fun save(result: List<Dto>, mode: SyncMode)
}