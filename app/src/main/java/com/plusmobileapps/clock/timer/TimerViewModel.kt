package com.plusmobileapps.clock.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import javax.inject.Inject

class TimerViewModel @Inject constructor(private val timerRepository: TimerRepository) : ViewModel() {

    val timers: LiveData<List<Timer>> = timerRepository.getTimers()
    val timerClicked = SingleLiveEvent<Unit>()
    private val timePickerTime = MutableLiveData<String>()
    private val time = 0

    fun getTimePickerTime() : LiveData<String> = timePickerTime

    fun timerAddClicked() = timerClicked.call()

    fun addTimer(seconds: Int, minutes: Int, hours: Int) {
        timerRepository.saveTimer(Timer(startTimeMillis = 1000, currentTimeLeftMillis = 1000))
    }

    fun deleteTimer(position: Int) {
        timers.value?.let {
            timerRepository.deleteTimer(it[position])
        }
    }

    fun resetTimer(position: Int) {
        timers.value?.let {
            val timer = it[position].apply {
                resetTimer()
            }
            timerRepository.saveTimer(timer)
        }
    }

    fun toggleTimer(position: Int) {
        timers.value?.let {
            val timer = it[position]
        }
    }

    fun numberClicked(number: String) {
        val digit = number.toInt()

    }
}