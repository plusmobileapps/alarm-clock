package com.plusmobileapps.clock.timer

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.TIMER_NOTIFICATION_CHANNEL_ID
import com.plusmobileapps.clock.main.MainActivity

class TimerService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, TIMER_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.)) // set the time)
                .setContentText(getText(R.string.title_timer))
                .setSmallIcon(R.drawable.ic_hourglass_empty_white_24px)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.timer_ticker_text_that_plays_for_accessibility))
                .build()
    }
}