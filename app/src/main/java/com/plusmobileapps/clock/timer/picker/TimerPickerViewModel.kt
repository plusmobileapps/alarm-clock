package com.plusmobileapps.clock.timer.picker

import androidx.lifecycle.*
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import java.util.Stack
import javax.inject.Inject
import kotlin.math.min

class TimerPickerViewModel @Inject constructor(private val timerRepository: TimerRepository): ViewModel() {

    private val seconds = MutableLiveData<Int>()
    private val minutes = MutableLiveData<Int>()
    private val hours = MutableLiveData<Int>()
    val timerButtonStartEvent = SingleLiveEvent<Unit>()
    val snackbarError = SingleLiveEvent<Unit>()
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
        if (!timerStack.empty()) {
            timerStack.pop()
            update()
        }
    }

    fun onTimerStartedFabClick() {
        val totalTime = getTotalTimeInMillis()
        if (totalTime != 0) {
            timerRepository.saveTimer(Timer(startTimeMillis = totalTime.toLong(), currentTimeLeftMillis = totalTime.toLong()))
            timerButtonStartEvent.call()
        } else {
            snackbarError.call()
        }
    }

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

    fun getDisplayTime(number: Int) : String {
        return when (number) {
            0 -> "00"
            1,2,3,4,5,6,7,8,9 -> "0$number"
            else -> number.toString()
        }
    }

    private fun getTotalTimeInMillis() : Int {
        val seconds = seconds.value ?: 0
        val minutes = minutes.value ?: 0
        val hours = hours.value ?: 0

        val totalSeconds = seconds + (minutes * 60) + (hours * 60 * 60)
        return totalSeconds * 1000
    }

}