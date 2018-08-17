package com.plusmobileapps.clock

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.plusmobileapps.clock.di.AppComponent
import com.plusmobileapps.clock.di.AppModule
import com.plusmobileapps.clock.di.DaggerAppComponent
import com.plusmobileapps.clock.di.RoomModule
import com.plusmobileapps.clock.util.supportsPlatform

const val TIMER_NOTIFICATION_CHANNEL_ID = "Timer Notification Channel"

class MyApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule(this))
                .build()
        supportsPlatform(Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_timer)
            val description = getString(R.string.notification_channel_timer_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(TIMER_NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }
}