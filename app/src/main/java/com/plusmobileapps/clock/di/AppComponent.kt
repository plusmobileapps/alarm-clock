package com.plusmobileapps.clock.di

import com.plusmobileapps.clock.alarm.AlarmFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RoomModule::class))
interface AppComponent {

    fun inject(alarmFragment: AlarmFragment)

}