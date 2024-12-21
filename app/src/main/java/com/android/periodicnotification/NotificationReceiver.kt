package com.android.periodicnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.periodicnotification.Constants.CHANNEL_ID
import com.android.periodicnotification.Constants.CHANNEL_NAME

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context != null) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)

                builder.apply {
                    setSmallIcon(R.drawable.small_icon)
                    setContentTitle("Periodic Notification")
                    setContentText("Notification Text")
                }

            } else {
                builder.apply {
                    setSmallIcon(R.drawable.small_icon)
                    setContentTitle("Periodic Notification")
                    setContentText("Notification Text")
                    setPriority(NotificationCompat.PRIORITY_DEFAULT)
                }
            }

            val notificationMangerCompat = NotificationManagerCompat.from(context)
            notificationMangerCompat.notify(1, builder.build())
        }
    }
}