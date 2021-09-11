package org.mixitconf.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED != intent.action) return
        enqueueWorkers(context)
    }

    private fun enqueueWorkers(context: Context) {
        // FiveYearWarrantyTriggerWorker.enqueuePeriodicWorker(context)
    }
}
