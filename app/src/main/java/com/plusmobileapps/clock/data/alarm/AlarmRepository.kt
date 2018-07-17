package com.plusmobileapps.clock.data.alarm

import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao): AlarmDataSource {

    override fun getAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAll()
    }

    override fun getAlarm(id: Int): LiveData<Alarm> {
        return alarmDao.getById(id)
    }

    override fun saveAlarm(alarm: Alarm) {
        doAsync { alarmDao.insert(alarm) }
    }

    override fun deleteAlarm(alarm: Alarm) {
        doAsync { alarmDao.delete(alarm) }
    }
}