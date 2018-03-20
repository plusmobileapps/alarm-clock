package com.plusmobileapps.clock.data

interface TimerDataSource {

    interface LoadTimersCallback {
        fun onTimersLoaded()
    }

}