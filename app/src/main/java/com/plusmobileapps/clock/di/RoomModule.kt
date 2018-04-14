package com.plusmobileapps.clock.di

import android.arch.persistence.room.Room
import com.plusmobileapps.clock.ViewModelFactory
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
            "appDb.db"
    ).build()

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao) = AlarmRepository(alarmDao)

    @Provides
    @Singleton
    fun providesAlarmDao(database: AppDatabase) = database.alarmDao()

    @Provides
    @Singleton
    fun providesRoomDatabase(application: MyApplication) = database

    @Provides
    @Singleton
    fun provideViewModelFactory(alarmRepository: AlarmRepository) = ViewModelFactory(alarmRepository)

}