package com.plusmobileapps.clock.data.timer

import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(private val timerDao: TimerDao) : TimerDataSource {

    override fun getTimers(): LiveData<List<Timer>> {
        return timerDao.getAll()
    }

    override fun saveTimer(timer: Timer) {
        doAsync { timerDao.insert(timer) }
    }

    override fun deleteTimer(timer: Timer) {
        doAsync { timerDao.delete(timer) }
    }
}