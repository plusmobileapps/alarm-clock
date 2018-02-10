package com.plusmobileapps.clock

import com.plusmobileapps.clock.alarm.AlarmFragment
import com.plusmobileapps.clock.stopwatch.StopwatchFragment
import com.plusmobileapps.clock.timer.TimerFragment

object FragmentFactory {

    private val alarmFragment by lazy {
        AlarmFragment.newInstance()
    }

    private val timerFragment by lazy {
        TimerFragment.newInstance()
    }

    private val stopwatchFragment by lazy {
        StopwatchFragment.newInstance()
    }

    fun alarmFragmentInstance() = alarmFragment

    fun timerFragmentInstance() = timerFragment

    fun stopwatchInstance() = stopwatchFragment

}