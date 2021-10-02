package org.mixitconf.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.mixitconf.workers.DataSynchronizationWorker
import timber.log.Timber

class BootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED != intent.action) return
        Timber.e("After boot we enqueue DataSynchronizationWorker")
        enqueueWorkers(context)
    }

    private fun enqueueWorkers(context: Context) {
        DataSynchronizationWorker.enqueuePeriodicWorker(context)
    }
}
