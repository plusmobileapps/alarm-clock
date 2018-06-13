package com.plusmobileapps.clock.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.plusmobileapps.clock.data.entities.Alarm

interface AlarmDataSource {
    fun getAlarms(): LiveData<List<Alarm>>
    fun getAlarm(id: Int): LiveData<Alarm>
    fun saveAlarm(alarm: Alarm)
    fun deleteAlarm(alarm: Alarm)
}