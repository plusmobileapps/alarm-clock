package com.plusmobileapps.clock.alarm.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.data.alarm.AlarmRepository
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.alarm.Alarm
import javax.inject.Inject


class AlarmLandingViewModel @Inject constructor(private val alarmRepository: AlarmRepository): ViewModel() {

    private val alarms: LiveData<List<Alarm>> = alarmRepository.getAlarms()
    val showTimePicker = SingleLiveEvent<Alarm?>()
    val openAlarm = SingleLiveEvent<Int>()

    fun getAlarms(): LiveData<List<Alarm>> = alarms

    fun getAlarmById(id: Int): LiveData<Alarm> = alarmRepository.getAlarm(id)

    fun addAlarm(hour: Int, min: Int) {
        val alarm = Alarm(hour = hour, min = min)
        alarmRepository.saveAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = alarmRepository.saveAlarm(alarm)

    fun openAlarm(alarm: Alarm) {
        openAlarm.value = alarm.id
    }

    fun updateAlarmToggle(enabled: Boolean, alarm: Alarm) {
        val newAlarm = alarm.copy(enabled = enabled)
        alarmRepository.saveAlarm(newAlarm)
    }

    fun editAlarmTimeClicked(alarm: Alarm) {
        showTimePicker.value = alarm
    }

    fun fabClicked() = showTimePicker.call()

    fun deleteAlarm(alarm: Alarm) = alarmRepository.deleteAlarm(alarm)

}
