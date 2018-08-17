package com.plusmobileapps.clock.di

import com.plusmobileapps.clock.alarm.detail.AlarmDetailActivity
import com.plusmobileapps.clock.alarm.landing.AlarmFragment
import com.plusmobileapps.clock.main.MainActivity
import com.plusmobileapps.clock.timer.landing.TimerFragment
import com.plusmobileapps.clock.timer.picker.TimerPickerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RoomModule::class, ViewModelModule::class))
interface AppComponent {

    fun inject(alarmFragment: AlarmFragment)
    fun inject(alarmDetailActivity: AlarmDetailActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(timerFragment: TimerFragment)
    fun inject(timerPickerFragment: TimerPickerFragment)
}