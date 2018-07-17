package com.plusmobileapps.clock.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.plusmobileapps.clock.data.alarm.AlarmDao
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerDao

@Database(entities = arrayOf(Alarm::class, Timer::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun timerDao(): TimerDao

    companion object {

        fun getInstance(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appDb.db"
        ).build()

    }


}
