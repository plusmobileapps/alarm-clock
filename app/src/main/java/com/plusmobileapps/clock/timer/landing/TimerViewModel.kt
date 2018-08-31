package com.plusmobileapps.clock.timer.landing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import javax.inject.Inject

class TimerViewModel @Inject constructor(private val timerRepository: TimerRepository) : ViewModel() {

    val timers: LiveData<List<Timer>> = timerRepository.getTimers()

    fun deleteTimer(timer: Timer) = timerRepository.deleteTimer(timer)

    fun resetTimer(timer: Timer) = timerRepository.saveTimer(timer)

    fun toggleTimer(timer: Timer) {
        Log.d("Timer", "timer was toggled!")
    }
}