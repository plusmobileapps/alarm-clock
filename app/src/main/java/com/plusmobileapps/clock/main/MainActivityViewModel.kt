package com.plusmobileapps.clock.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.R
import javax.inject.Inject

sealed class MainActivityViewState {
    object Alarm : MainActivityViewState()
    object Timer : MainActivityViewState()
    object StopWatch : MainActivityViewState()
}

class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val viewState = MutableLiveData<MainActivityViewState>()

    fun getViewStateLiveData() : LiveData<MainActivityViewState> {
        return viewState
    }

    fun navigationClicked(navResourceId: Int) : Boolean {
        return when(navResourceId) {
            R.id.navigation_alarm -> {
                isNotCurrentScreen(MainActivityViewState.Alarm)
                true
            }
            R.id.navigation_timer -> {
                isNotCurrentScreen(MainActivityViewState.Timer)
                true
            }
            R.id.navigation_stopwatch -> {
                isNotCurrentScreen(MainActivityViewState.StopWatch)
                true
            }
            else -> false
        }
    }

    private fun isNotCurrentScreen(request: MainActivityViewState) {
        val currentViewState = viewState.value ?: MainActivityViewState.Alarm
        if (request != currentViewState) {
            viewState.value = request
        }
    }


}