package com.plusmobileapps.clock.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.plusmobileapps.clock.FragmentProvider

class MainSwipeAdapter(fragmentManager: androidx.fragment.app.FragmentManager): androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when(position) {
            BottomNav.ALARM.ordinal -> FragmentProvider.alarmFragmentInstance()
            BottomNav.TIMER.ordinal -> FragmentProvider.timerFragmentInstance()
            BottomNav.STOPWATCH.ordinal -> FragmentProvider.stopwatchInstance()
            else -> androidx.fragment.app.Fragment()
        }
    }

    override fun getCount() = BottomNav.values().size
}