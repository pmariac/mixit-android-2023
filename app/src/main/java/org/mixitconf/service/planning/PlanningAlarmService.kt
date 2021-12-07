package org.mixitconf.service.planning

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import org.mixitconf.Properties
import org.mixitconf.model.Talk
import org.mixitconf.receivers.PlanningAlarmReceiver
import org.mixitconf.service.AppPreferences
import java.time.Duration
import java.time.Instant


class PlanningAlarmService(private val context: Context) {

    private fun getAlarmManager() =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setTalkAlarm(talk: Talk) {
        if (AppPreferences.mayNotifyBeforeTalkStart) {
            // Alarm is setted to be executed 5 minutes before its start
            val durationInMillis = Duration.between(Instant.now(), talk.startTime.toInstant()).minusMinutes(5).toMillis()
            if (durationInMillis > 0) {
                getAlarmManager()
                    .set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + durationInMillis, getPendingIntent(talk))
            }
        }
        // We have to check user preference to know if we have to create an alarm or not
    }

    private fun getPendingIntent(talk: Talk) =
        PendingIntent.getBroadcast(
            context,
            talk.id!!.toInt(),
            Intent(context, PlanningAlarmReceiver::class.java).putExtra(Properties.TALK_ID, talk.id),
            FLAG_IMMUTABLE or FLAG_CANCEL_CURRENT
        )

    fun cancelTalkAlarm(talk: Talk) {
        getAlarmManager().cancel(getPendingIntent(talk))
    }

}