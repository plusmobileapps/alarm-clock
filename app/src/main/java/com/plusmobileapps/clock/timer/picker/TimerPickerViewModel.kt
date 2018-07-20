package com.plusmobileapps.clock.timer.picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.SingleLiveEvent

class TimerPickerViewModel : ViewModel() {

    val seconds = MutableLiveData<Int>()
    val minutes = MutableLiveData<Int>()
    val hours = MutableLiveData<Int>()
    val timerButtonClick = SingleLiveEvent<Unit>()

    fun getSeconds() : LiveData<Int> = seconds

    fun getMinutes() : LiveData<Int> = minutes

    fun getHours() : LiveData<Int> = hours

    fun onNumberClicked(number: String) {

    }

    fun onTimerStarted() = timerButtonClick.call()

}