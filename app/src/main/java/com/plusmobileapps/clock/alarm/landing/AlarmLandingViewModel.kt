package com.plusmobileapps.clock.alarm.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.data.alarm.AlarmRepository
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.alarm.Alarm
import javax.inject.Inject


class AlarmLandingViewModel @Inject constructor(private val alarmRepository: AlarmRepository): ViewModel() {

    private val alarms: LiveData<List<Alarm>> = alarmRepository.getAlarms()
    val showTimePickerToggle = SingleLiveEvent<Unit>()

    fun getAlarms(): LiveData<List<Alarm>> = alarms

    fun getAlarm(position: Int): Alarm? = alarms.value?.get(position)

    fun getAlarmId(position: Int): Int? = alarms.value?.get(position)?.id

    fun getAlarmById(id: Int): LiveData<Alarm> = alarmRepository.getAlarm(id)

    fun addAlarm(hour: Int, min: Int) {
        val alarm = Alarm(hour = hour, min = min)
        alarmRepository.saveAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = alarmRepository.saveAlarm(alarm)

    fun updateAlarmToggle(enabled: Boolean, position: Int) {
        alarms.value?.let {
            val alarm = it[position]
            alarm.enabled = enabled
            alarmRepository.saveAlarm(alarm)
        }
    }

    fun showTimePicker() = showTimePickerToggle.call()

    fun deleteAlarm(alarm: Alarm) = alarmRepository.deleteAlarm(alarm)

}