package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseServices: FirebaseMessagingService() {
    //Generates userToken
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("Token", token)
    }
    //Creating notification from data received
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.data["title"]
        val text = message.data["message"]
        Log.i("Response", title.toString())
        Log.i("Response", text.toString())
        val CHANNEL_ID = "Message"
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Message Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        with(NotificationManagerCompat.from(this)) {
            notify(12, notification.build())
        }
    }
}