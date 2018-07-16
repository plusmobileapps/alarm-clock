package com.plusmobileapps.clock.data

import androidx.lifecycle.LiveData
import com.plusmobileapps.clock.data.daos.AlarmDao
import com.plusmobileapps.clock.data.entities.Alarm
import com.plusmobileapps.clock.ioThread
import org.jetbrains.anko.doAsync
import javax.inject.Inject

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