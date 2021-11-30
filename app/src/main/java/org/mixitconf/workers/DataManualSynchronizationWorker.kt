package org.mixitconf.workers

import android.content.Context
import androidx.work.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mixitconf.R
import org.mixitconf.model.Speaker
import org.mixitconf.model.Talk
import org.mixitconf.service.notification.NotificationChannelManager
import org.mixitconf.service.notification.NotificationChannelManager.NotificationOption
import org.mixitconf.service.synchronization.SpeakerSynchronizationService
import org.mixitconf.service.synchronization.SynchronizationService.Companion.SyncMode.MANUAL
import org.mixitconf.service.synchronization.TalkSynchronizationService
import timber.log.Timber

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
        val result = try {
            (speakerSynchronizationService.synchronize<Speaker>(MANUAL).result &&
                    talkSynchronizationService.synchronize<Talk>(MANUAL).result)
        } catch (e: Exception) {
            Timber.w(e, "Error trying to synchronize data: ${e.message}")
            false
        }
        NotificationChannelManager.createSyncNotification(
            applicationContext,
            NotificationOption(
                applicationContext.getString(R.string.settings_sync_data),
                applicationContext.getString(if (result) R.string.info_sync_data else R.string.error_sync_data)
            )
        )
        return if (result) Result.success() else Result.failure()
    }

}
