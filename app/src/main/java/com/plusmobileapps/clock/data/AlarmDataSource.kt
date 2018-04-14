package com.plusmobileapps.clock.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.plusmobileapps.clock.data.entities.Alarm

interface AlarmDataSource {
    fun getAlarms(): LiveData<List<Alarm>>
    fun getAlarm(id: Int): LiveData<Alarm>
    fun saveAlarm(alarm: Alarm)
    fun deleteAlarm(alarm: Alarm)
}