package com.example.originalecommerce.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.originalecommerce.R
import com.example.originalecommerce.models.Product
import com.example.originalecommerce.ui.body.BodyActivity
import com.example.originalecommerce.utils.Constants
import com.google.android.datatransport.cct.internal.LogResponse.fromJson
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class MyFirebaseMessagingService:FirebaseMessagingService() {
    val json=Gson()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("warfme",p0)
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        val prod=msg.data["product"]
        val bundle = Bundle()
        bundle.putString(Constants.PRODUCT_BYNDLE_KEY,prod)
        val intent=Intent(this,BodyActivity::class.java)
        val notificationManger=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID=Random.nextInt()

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            creatNotifChannel(notificationManger)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent2 = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.body_nav)
            .setDestination(R.id.showPeoductFragment)
            .setArguments(bundle)
            .setComponentName(BodyActivity::class.java)
            .createPendingIntent()

        val pendingIntent=PendingIntent.getActivity(this,1,intent,FLAG_ONE_SHOT)
        val notification=NotificationCompat.Builder(this,"myId")
            .setContentTitle(msg.data["title"])
            .setContentText(msg.data["msg"])
            .setSmallIcon(R.drawable.icon_cart)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent2)
            .build()

        notificationManger.notify(notificationID,notification)

    }

    fun creatNotifChannel(notiManger:NotificationManager)
    {
        notiManger.createNotificationChannel(
            NotificationChannel("myId","channelname",IMPORTANCE_HIGH).apply {
                description=""
                enableLights(true)
                lightColor=Color.GREEN
            }
        )
    }
}