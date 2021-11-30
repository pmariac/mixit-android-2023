package org.mixitconf.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mixitconf.Properties
import org.mixitconf.R
import org.mixitconf.database.TalkRepository
import org.mixitconf.service.notification.NotificationChannelManager
import org.mixitconf.service.notification.NotificationChannelManager.NotificationOption

class PlanningAlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val talkRepository: TalkRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        val talkId = intent.getLongExtra(Properties.TALK_ID, 0L)

        runBlocking {
            val talk = talkRepository.readOne(talkId)
            if (talk != null) {
                NotificationChannelManager.createPlanningNotification(
                    context,
                    NotificationOption(
                        talk.title,
                        context.getString(R.string.planning_notification_description, talk.roomName(context.resources))
                    )
                )
            }
        }
    }
}
