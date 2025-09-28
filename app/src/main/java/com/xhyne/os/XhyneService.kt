package com.xhyne.os

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class XhyneService : Service() {

    private val CHANNEL_ID = "XhyneServiceChannel"
    private val NOTIFICATION_ID = 1
    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        // Create the notification in onCreate as requested
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Xhyne Service")
            .setContentText("Xhyne is running.")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Using a generic Android icon
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the service in the foreground
        startForeground(NOTIFICATION_ID, notification)

        // Indicate that the service should be re-created if its process is killed
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Xhyne Service Channel",
                NotificationManager.IMPORTANCE_LOW // Low importance for persistent background services
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }
}