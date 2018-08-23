package com.plusmobileapps.clock.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.R
import java.util.*
import javax.inject.Inject

sealed class ViewState {
    object Alarm : ViewState()
    object Timer : ViewState()
    object Stopwatch : ViewState()
}

class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val viewState = MutableLiveData<ViewState>().apply {
        value = ViewState.Alarm
    }

    fun getViewStateLiveData() : LiveData<ViewState> = viewState

}