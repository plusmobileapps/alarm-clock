package com.plusmobileapps.clock.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.plusmobileapps.clock.FragmentFactory

class MainSwipeAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            BottomNav.ALARM.ordinal -> FragmentFactory.alarmFragmentInstance()
            BottomNav.TIMER.ordinal -> FragmentFactory.timerFragmentInstance()
            BottomNav.STOPWATCH.ordinal -> FragmentFactory.stopwatchInstance()
            else -> Fragment()
        }
    }

    override fun getCount() = BottomNav.values().size
}