package com.plusmobileapps.clock.alarm

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.plusmobileapps.clock.data.Repository

class AlarmViewModel: ViewModel() {

    private lateinit var repository: Repository

    fun init(repository: Repository) {
        this.repository = repository
    }



}