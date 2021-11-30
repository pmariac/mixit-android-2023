package org.mixitconf.service.planning

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import org.mixitconf.model.Talk
import org.mixitconf.receivers.PlanningAlarmReceiver
import org.mixitconf.service.AppPreferences


class PlanningAlarmService(private val context: Context) {

    private fun getAlarmManager() =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setTalkAlarm(talk: Talk) {
        // We have to check user preference to know if we have to create an alarm or not
        if (AppPreferences.mayNotifyBeforeTalkStart) {
            // Alarm is setted to be executed 15 minutes
            // TODO set talk start time
            getAlarmManager()
                .set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6000, getPendingIntent(talk))
        }
    }

    private fun getPendingIntent(talk: Talk) =
        PendingIntent.getBroadcast(
            context,
            talk.id!!.toInt(),
            Intent(context, PlanningAlarmReceiver::class.java),
            FLAG_IMMUTABLE or FLAG_CANCEL_CURRENT
        )

    fun cancelTalkAlarm(talk: Talk) {
        getAlarmManager().cancel(getPendingIntent(talk))
    }

}