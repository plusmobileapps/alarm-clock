package com.plusmobileapps.clock.data

import android.arch.lifecycle.MutableLiveData
import com.plusmobileapps.clock.data.entities.Alarm

interface AlarmDataSource {
    fun getAlarms(): MutableLiveData<List<Alarm>>
    fun getAlarm(id: Int): MutableLiveData<Alarm>
    fun saveAlarm(alarm: Alarm)
    fun deleteAlarm(alarm: Alarm)
}