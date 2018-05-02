package com.plusmobileapps.clock.di

import android.arch.persistence.room.Room
import android.content.Context
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import com.plusmobileapps.clock.data.AppDatabase
import com.plusmobileapps.clock.data.daos.AlarmDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun providesRoomDatabase() = Room.databaseBuilder(
                appContext.applicationContext,
                AppDatabase::class.java,
                "appDb.db")
                .build()

    @Provides
    @Singleton
    fun providesAlarmDao(database: AppDatabase) : AlarmDao = database.alarmDao()

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao) = AlarmRepository(alarmDao)

    @Provides
    fun provideAlarmViewModel(alarmRepository: AlarmRepository) = AlarmLandingViewModel(alarmRepository)
}