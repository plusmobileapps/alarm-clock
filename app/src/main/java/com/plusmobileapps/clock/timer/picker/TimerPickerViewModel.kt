package com.plusmobileapps.clock.timer.picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.SingleLiveEvent
import java.util.Stack
import javax.inject.Inject

class TimerPickerViewModel @Inject constructor(): ViewModel() {

    private val seconds = MutableLiveData<Int>()
    private val minutes = MutableLiveData<Int>()
    private val hours = MutableLiveData<Int>()
    val timerButtonClickEvent = SingleLiveEvent<Unit>()
    private val timerStack = Stack<Int>()

    fun getSeconds() : LiveData<Int> = seconds
    fun getMinutes() : LiveData<Int> = minutes
    fun getHours() : LiveData<Int> = hours

    fun onNumberClicked(number: String) {
        if (timerStack.size <= 6) {
            timerStack.push(number.toInt())
            update()
        }
    }

    fun onDeleteClicked() {
        timerStack.pop()
        update()
    }

    fun onTimerStartedFabClick() = timerButtonClickEvent.call()

    private fun update() {
        val totalNumbers = timerStack.size
        val stack = Stack<Int>().apply { addAll(timerStack) }
        when (totalNumbers) {
            0 -> {
                seconds.value = 0
                minutes.value = 0
                hours.value = 0
            }
            1 -> {
                seconds.value = stack.pop()
                minutes.value = 0
                hours.value = 0
            }
            2 -> {
                updateSeconds(stack)
                minutes.value = 0
                hours.value = 0
            }
            3 -> {
                updateSeconds(stack)
                minutes.value = stack.pop()
                hours.value = 0
            }
            4 -> {
                updateSeconds(stack)
                updateMinutes(stack)
                hours.value = 0
            }
            5 ->  {
                updateSeconds(stack)
                updateMinutes(stack)
                hours.value = stack.pop()
            }
            6 -> {
                updateSeconds(stack)
                updateMinutes(stack)
                updateHours(stack)
            }
        }
    }

    private fun updateSeconds(stack: Stack<Int>) {
        var seconds = stack.pop()
        seconds += (stack.pop() * 10)
        this.seconds.value = seconds
    }

    private fun updateMinutes(stack: Stack<Int>) {
        var minutes = stack.pop()
        minutes += (stack.pop() * 10)
        this.minutes.value = minutes
    }

    private fun updateHours(stack: Stack<Int>) {
        var hours = stack.pop()
        hours += (stack.pop() * 10)
        this.hours.value = hours
    }

}