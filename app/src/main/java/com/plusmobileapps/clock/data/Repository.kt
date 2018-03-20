package com.plusmobileapps.clock.data

import android.arch.lifecycle.MutableLiveData
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.data.entities.Alarm

object Repository: AlarmDataSource {

    private var cacheIsDirty = false
    private var cachedAlarms = MutableLiveData<List<Alarm>>()
    private val database = MyApplication.getDatabase()

    override fun getAlarms(): MutableLiveData<List<Alarm>> {
        return database.alarmDao().getAll()
    }

    override fun getAlarm(id: Int): MutableLiveData<Alarm> {
        return database.alarmDao().getById(id)
    }

    override fun saveAlarm(alarm: Alarm) {
        database.alarmDao().insert(alarm)
    }

    override fun deleteAlarm(alarm: Alarm) {
        database.alarmDao().delete(alarm)
    }
}