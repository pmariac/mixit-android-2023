package org.mixitconf.workers

import android.content.Context
import androidx.work.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mixitconf.Properties.DATA_SYNC_INTERVAL
import org.mixitconf.Properties.DATA_SYNC_INTERVAL_TIMEUNIT
import org.mixitconf.Properties.DATA_SYNC_WORKER_NAME
import org.mixitconf.model.entity.Talk
import org.mixitconf.service.synchronization.SpeakerSynchronizationService
import org.mixitconf.service.synchronization.SynchronizationService.Companion.SyncMode.BACKGROUND
import org.mixitconf.service.synchronization.TalkSynchronizationService
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * This coroutineWorker checks in the background for data synchronization
 */
class DataManualSynchronizationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val talkSynchronizationService: TalkSynchronizationService by inject()
    private val speakerSynchronizationService: SpeakerSynchronizationService by inject()

    companion object {
        fun enqueueManualWorker(context: Context) {
            val work = OneTimeWorkRequestBuilder<DataManualSynchronizationWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context).enqueue(work)
        }
    }

    override suspend fun doWork(): Result {
        return try {
            talkSynchronizationService.synchronize<Talk>(BACKGROUND)
            speakerSynchronizationService.synchronize<Talk>(BACKGROUND)
            Result.success()
        } catch (e: Exception) {
            Timber.w(e, "Error trying to synchronize data: ${e.message}")
            Result.failure()
        }
    }
}
