package org.mixitconf.service.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import org.mixitconf.R
import org.mixitconf.ui.home.MainActivity

object NotificationChannelManager {

    private fun getNotificationManager(context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun createNotificationId() = Math.random().toInt()

    fun createPlanningNotificationChannel(context: Context) {
        createNotificationChannel(
            context = context,
            channelIdResId = R.string.planning_notification_channel_id,
            channelNameResId = R.string.planning_notification_channel_name,
            channelDescriptionResId = R.string.planning_notification_channel_description
        )
    }

    fun createSyncNotificationChannel(context: Context) {
        createNotificationChannel(
            context = context,
            channelIdResId = R.string.sync_notification_channel_id,
            channelNameResId = R.string.sync_notification_channel_name,
            channelDescriptionResId = R.string.sync_notification_channel_description,
            importance = NotificationManager.IMPORTANCE_LOW
        )
    }

    data class NotificationOption(
        val title: String?,
        val message: String,
        val iconResId: Int = R.drawable.mxt_logo_x,
        val intent: Intent? = null
    )

    fun createSyncNotification(context: Context, data: NotificationOption) {
        showNotification(context, createNotificationId(), R.string.sync_notification_channel_id, data)
    }

    fun createPlanningNotification(context: Context, data: NotificationOption) {
        showNotification(context, createNotificationId(), R.string.planning_notification_channel_id, data)
    }

    @VisibleForTesting
    fun getPendingIntent(context: Context, intent: Intent): PendingIntent? {
        return TaskStackBuilder.create(context).run {
            addParentStack(MainActivity::class.java)
            addNextIntent(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }


    private fun showNotification(
        context: Context,
        notificationId: Int,
        @StringRes channelIdResId: Int,
        notificationOption: NotificationOption
    ): Int {
        val pendingIntent = notificationOption.intent?.let {
            TaskStackBuilder.create(context).run {
                addParentStack(MainActivity::class.java)
                addNextIntent(it)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }
        }
        val channelId = context.getString(channelIdResId)
        val notification = NotificationCompat.Builder(context, channelId)
            .setColor(context.getColor(R.color.colorPrimary))
            .setSmallIcon(notificationOption.iconResId)
            .setContentTitle(notificationOption.title)
            .setContentText(notificationOption.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationOption.message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // actions.forEach { builder.addAction(it.action) }
        getNotificationManager(context).notify(notificationId, notification)
        return notificationId
    }

    private fun createNotificationChannel(
        context: Context,
        @StringRes channelIdResId: Int,
        @StringRes channelNameResId: Int,
        @StringRes channelDescriptionResId: Int,
        importance: Int = NotificationManager.IMPORTANCE_HIGH
    ) {
        val channelId = context.getString(channelIdResId)
        val name = context.getString(channelNameResId)
        val descriptionText = context.getString(channelDescriptionResId)
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        getNotificationManager(context).createNotificationChannel(channel)
    }
}