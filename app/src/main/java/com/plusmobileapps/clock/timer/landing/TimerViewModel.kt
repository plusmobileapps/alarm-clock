package com.plusmobileapps.clock.timer.landing

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import javax.inject.Inject

const val ONE_SECOND = 1000L

class TimerViewModel @Inject constructor(private val timerRepository: TimerRepository) : ViewModel() {

    val timers: LiveData<List<Timer>> = timerRepository.getTimers()
    val showTimerPicker = SingleLiveEvent<Unit>()
    private val mapTimers = mutableMapOf<Int, CountDownTimer>()
    val playRingtone = SingleLiveEvent<Unit>()

    fun deleteTimer(timer: Timer) = timerRepository.deleteTimer(timer)


    fun resetTimer(timer: Timer) = timerRepository.saveTimer(timer)

    fun addTimerButtonClicked() = showTimerPicker.call()

    fun toggleTimer(timer: Timer) {
        if (timer.isInProgress) stopTimer(timer) else startTimer(timer)
    }

    private fun startTimer(timer: Timer) {
        val key = timer.id ?: return
        timerRepository.saveTimer(timer.copy(isInProgress = true))
        mapTimers[key] = object : CountDownTimer(timer.originalStartTimeMillis, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                val progress = millisUntilFinished / timer.originalStartTimeMillis
                timerRepository.saveTimer(timer.copy(startTimeMillis = millisUntilFinished, progress = progress.toInt()))
            }

            override fun onFinish() = timerFinished(timer)

        }.start()
    }

    private fun stopTimer(timer: Timer) {
        val key = timer.id ?: return
        val countDownTimer = mapTimers[key]
        countDownTimer?.cancel()
        mapTimers.remove(key)
        timerRepository.saveTimer(timer.copy(isInProgress = false))
    }

    private fun timerFinished(timer: Timer) {
        val key = timer.id ?: return
        mapTimers.remove(key)
        timerRepository.saveTimer(timer.copy(isInProgress = false))
        playRingtone.call()
    }
}