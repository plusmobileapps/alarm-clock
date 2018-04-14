package com.plusmobileapps.clock.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.plusmobileapps.clock.data.daos.AlarmDao
import com.plusmobileapps.clock.data.entities.Alarm

@Database(entities = arrayOf(Alarm::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {

        fun getInstance(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appDb.db"
        ).build()

    }


}
