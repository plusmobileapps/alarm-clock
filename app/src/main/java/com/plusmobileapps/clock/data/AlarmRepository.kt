package com.plusmobileapps.clock.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.plusmobileapps.clock.data.daos.AlarmDao
import com.plusmobileapps.clock.di.MyApplication
import com.plusmobileapps.clock.data.entities.Alarm
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao): AlarmDataSource {

    override fun getAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAll()
    }

    override fun getAlarm(id: Int): LiveData<Alarm> {
        return alarmDao.getById(id)
    }

    override fun saveAlarm(alarm: Alarm) {
        alarmDao.insert(alarm)
    }

    override fun deleteAlarm(alarm: Alarm) {
        alarmDao.delete(alarm)
    }
}