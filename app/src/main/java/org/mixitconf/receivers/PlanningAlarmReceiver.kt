package org.mixitconf.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PlanningAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TODO implements notification
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show()
    }
}
