package com.plusmobileapps.clock.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.plusmobileapps.clock.R

enum class BottomNav {
    ALARM, TIMER, STOPWATCH
}

class MainActivity() : AppCompatActivity() {

    private val navigation by lazy {
        findViewById<BottomNavigationView>(R.id.navigation)
    }

    private val viewPager by lazy {
        findViewById<ViewPager>(R.id.view_pager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewPager.apply {
            adapter = MainSwipeAdapter(supportFragmentManager)
            addOnPageChangeListener(pageChangeListener)
        }
    }


    private fun changePage(position: Int) {
        viewPager.currentItem = position
    }

    private fun changeNavHighlight(itemId: Int) {
        navigation.selectedItemId = itemId
    }

    /**
     * Handle Listeners
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_alarm -> {
                changePage(BottomNav.ALARM.ordinal)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {
                changePage(BottomNav.TIMER.ordinal)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                changePage(BottomNav.STOPWATCH.ordinal)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val pageChangeListener = object:ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) = changeNavHighlight(position)

    }
}
