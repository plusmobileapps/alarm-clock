package com.plusmobileapps.clock.di

import androidx.room.Room
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.data.alarm.AlarmRepository
import com.plusmobileapps.clock.data.AppDatabase
import com.plusmobileapps.clock.data.alarm.AlarmDao
import com.plusmobileapps.clock.data.timer.TimerDao
import com.plusmobileapps.clock.data.timer.TimerRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: MyApplication) {

    private val database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "appDb.db")
            .build()

    @Provides
    @Singleton
    fun providesRoomDatabase(): AppDatabase = database

    @Provides
    @Singleton
    fun providesAlarmDao(database: AppDatabase): AlarmDao = database.alarmDao()

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao) = AlarmRepository(alarmDao)

    @Provides
    @Singleton
    fun providesTimerDao(database: AppDatabase): TimerDao = database.timerDao()

    @Provides
    @Singleton
    fun providesTimerRepository(timerDao: TimerDao) = TimerRepository(timerDao)


}