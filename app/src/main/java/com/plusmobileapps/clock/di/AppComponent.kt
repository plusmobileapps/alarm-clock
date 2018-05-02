package com.plusmobileapps.clock.di

import com.plusmobileapps.clock.alarm.detail.AlarmDetailActivity
import com.plusmobileapps.clock.alarm.landing.AlarmFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RoomModule::class))
interface AppComponent {

    fun inject(alarmFragment: AlarmFragment)
    fun inject(alarmDetailActivity: AlarmDetailActivity)
}