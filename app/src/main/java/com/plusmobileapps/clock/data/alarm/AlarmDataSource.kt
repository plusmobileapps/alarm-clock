package com.plusmobileapps.clock.data.alarm

import androidx.lifecycle.LiveData
import com.plusmobileapps.clock.data.alarm.Alarm

interface AlarmDataSource {
    fun getAlarms(): LiveData<List<Alarm>>
    fun getAlarm(id: Int): LiveData<Alarm>
    fun saveAlarm(alarm: Alarm)
    fun deleteAlarm(alarm: Alarm)
}