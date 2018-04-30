package com.plusmobileapps.clock.alarm.landing

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import com.plusmobileapps.clock.data.entities.Alarm


class AlarmLandingViewModel(private val alarmRepository: AlarmRepository): ViewModel() {

    private val alarms = alarmRepository.getAlarms()

    fun getAlarms(): LiveData<List<Alarm>> = alarms

    fun getAlarm(position: Int): Alarm? {
        return alarms.value?.get(position)
    }

    fun getAlarmId(position: Int): Int? {
        return alarms.value?.get(position)?.id
    }

    fun addAlarm(hour: Int, min: Int) {
        val alarm = Alarm(hour = hour, min = min)
        alarmRepository.saveAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = alarmRepository.saveAlarm(alarm)


    fun updateAlarmToggle(enabled: Boolean, position: Int) {
        if (alarms.value != null) {
            val alarm = alarms.value!![position]
            alarm.enabled = enabled
            alarmRepository.saveAlarm(alarm)
        }
    }

}