package com.plusmobileapps.clock.data.timer

import androidx.lifecycle.LiveData

interface TimerDataSource {
    fun getTimers(): LiveData<List<Timer>>
    fun saveTimer(timer: Timer)
    fun deleteTimer(timer: Timer)
}