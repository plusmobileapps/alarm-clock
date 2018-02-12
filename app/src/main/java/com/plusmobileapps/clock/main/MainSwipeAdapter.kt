package com.plusmobileapps.clock.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.plusmobileapps.clock.FragmentProvider

class MainSwipeAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            BottomNav.ALARM.ordinal -> FragmentProvider.alarmFragmentInstance()
            BottomNav.TIMER.ordinal -> FragmentProvider.timerFragmentInstance()
            BottomNav.STOPWATCH.ordinal -> FragmentProvider.stopwatchInstance()
            else -> Fragment()
        }
    }

    override fun getCount() = BottomNav.values().size
}