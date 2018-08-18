package com.plusmobileapps.clock.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.SingleLiveEvent
import javax.inject.Inject
import kotlin.concurrent.timer

sealed class MainActivityViewState {
    object Alarm : MainActivityViewState()
    object Timer : MainActivityViewState()
    object TimerPicker : MainActivityViewState()
    object StopWatch : MainActivityViewState()
}

class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val viewState = MutableLiveData<MainActivityViewState>().apply {
        value = MainActivityViewState.Alarm
    }
    val killApp = SingleLiveEvent<Unit>()

    fun getViewStateLiveData() : LiveData<MainActivityViewState> {
        return viewState
    }

    @VisibleForTesting
    fun isNotCurrentScreen(request: MainActivityViewState) {
        val currentViewState = viewState.value ?: MainActivityViewState.Alarm
        if (request != currentViewState) {
            viewState.value = request
        }
    }

    fun addTimerClicked() {
        viewState.value = MainActivityViewState.TimerPicker
    }

    fun timerPickerFinished() {
        viewState.value = MainActivityViewState.Timer
    }

    fun onBackKeyPressed() {
        val viewState = viewState.value
        when(viewState) {
            MainActivityViewState.Alarm -> killApp.call()
            MainActivityViewState.Timer -> killApp.call()
            MainActivityViewState.TimerPicker -> timerPickerFinished()
            MainActivityViewState.StopWatch -> killApp.call()
        }
    }

}