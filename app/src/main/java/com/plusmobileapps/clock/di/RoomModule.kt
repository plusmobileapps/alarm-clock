package com.plusmobileapps.clock.di

import androidx.room.Room
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.data.AlarmRepository
import com.plusmobileapps.clock.data.AppDatabase
import com.plusmobileapps.clock.data.daos.AlarmDao
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
    fun providesAlarmDao(database: AppDatabase) : AlarmDao = database.alarmDao()

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao) = AlarmRepository(alarmDao)
    

}