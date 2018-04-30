package com.plusmobileapps.clock.alarm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import com.plusmobileapps.clock.data.entities.Alarm


class AlarmViewModel(private val alarmRepository: AlarmRepository): ViewModel() {

    fun getAlarms() = alarmRepository.getAlarms()

    fun getAlarm(id: Int) = alarmRepository.getAlarm(id)

    fun addAlarm(hour: Int, min: Int) {
        val alarm = Alarm(hour = hour, min = min)
        alarmRepository.saveAlarm(alarm)
    }

}