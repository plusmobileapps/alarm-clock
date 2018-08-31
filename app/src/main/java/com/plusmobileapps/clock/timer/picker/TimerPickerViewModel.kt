package com.plusmobileapps.clock.timer.picker

import androidx.lifecycle.*
import com.plusmobileapps.clock.SingleLiveEvent
import com.plusmobileapps.clock.data.timer.Timer
import com.plusmobileapps.clock.data.timer.TimerRepository
import java.util.Stack
import javax.inject.Inject

class TimerPickerViewModel @Inject constructor(private val timerRepository: TimerRepository): ViewModel() {

    private var seconds = 0
    private var minutes = 0
    private var hours = 0
    private val displaySeconds = MutableLiveData<String>()
    private val displayMinutes = MutableLiveData<String>()
    private val displayHours = MutableLiveData<String>()
    val timerButtonStartEvent = SingleLiveEvent<Unit>()
    val snackbarError = SingleLiveEvent<Unit>()
    private val timerStack = Stack<Int>()

    init {
        updateDisplayTimes()
    }

    fun getSeconds() : LiveData<String> = displaySeconds
    fun getMinutes() : LiveData<String> = displayMinutes
    fun getHours() : LiveData<String> = displayHours

    fun onNumberClicked(number: Int) {
        when(timerStack.size) {
            0 -> if (number != 0) pushNumberToStack(number)
            in 1..6 -> pushNumberToStack(number)
            else -> Unit
        }
    }

    private fun pushNumberToStack(number: Int) {
        timerStack.push(number)
        update()
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
            timerRepository.saveTimer(
                    Timer(startTimeMillis = totalTime.toLong(),
                            currentTimeLeftMillis = totalTime.toLong(),
                            timerText = "TODO: replace with timer text"))
            reset()
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
                seconds = 0
                minutes = 0
                hours = 0
            }
            1 -> {
                seconds = stack.pop()
                minutes = 0
                hours = 0
            }
            2 -> {
                seconds = stack.pop() + (stack.pop() * 10)
                minutes = 0
                hours = 0
            }
            3 -> {
                seconds = stack.pop() + (stack.pop() * 10)
                minutes = stack.pop()
                hours = 0
            }
            4 -> {
                seconds = stack.pop() + (stack.pop() * 10)
                minutes = stack.pop() + (stack.pop() * 10)
                hours = 0
            }
            5 ->  {
                seconds = stack.pop() + (stack.pop() * 10)
                minutes = stack.pop() + (stack.pop() * 10)
                hours = stack.pop()
            }
            6 -> {
                seconds = stack.pop() + (stack.pop() * 10)
                minutes = stack.pop() + (stack.pop() * 10)
                hours = stack.pop() + (stack.pop() * 10)
            }
        }
        updateDisplayTimes()
    }

    private fun updateDisplayTimes() {
        displaySeconds.value = getDisplayTime(seconds)
        displayMinutes.value = getDisplayTime(minutes)
        displayHours.value = getDisplayTime(hours)
    }

    fun getDisplayTime(number: Int) : String {
        return when (number) {
            0 -> "00"
            in 1..9 -> "0$number"
            else -> number.toString()
        }
    }

    private fun getTotalTimeInMillis() : Int {
        val totalSeconds = seconds + (minutes * 60) + (hours * 60 * 60)
        return totalSeconds * 1000
    }

    private fun reset() {
        hours = 0
        minutes = 0
        seconds = 0
        timerStack.clear()
        updateDisplayTimes()
    }

}