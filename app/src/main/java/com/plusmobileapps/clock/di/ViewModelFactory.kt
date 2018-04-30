package com.plusmobileapps.clock.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.plusmobileapps.clock.alarm.AlarmViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(
        private val alarmRepository: AlarmRepository): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            AlarmViewModel(alarmRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}