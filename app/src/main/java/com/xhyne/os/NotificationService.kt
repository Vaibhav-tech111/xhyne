package com.xhyne.os

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.app.Notification

class NotificationService : NotificationListenerService() {

    private val TAG = "NotificationService"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        val packageName = sbn.packageName
        val notification = sbn.notification
        val extras = notification.extras

        val title = extras.getString(Notification.EXTRA_TITLE)
        val text = extras.getString(Notification.EXTRA_TEXT)

        Log.d(TAG, "Notification Posted: Package = $packageName, Title = $title, Text = $text")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)

        val packageName = sbn.packageName
        Log.d(TAG, "Notification Removed: Package = $packageName")
    }
}