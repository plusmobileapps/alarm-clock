package com.plusmobileapps.clock.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.di.ViewModelFactory
import javax.inject.Inject

enum class BottomNav {
    ALARM, TIMER, STOPWATCH
}

class MainActivity() : AppCompatActivity() {

    private val coordinatorLayout by lazy { findViewById<CoordinatorLayout>(R.id.coordinator) }

    private val appBar by lazy { findViewById<BottomAppBar>(R.id.bottomAppBar) }

    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    private val fab by lazy { findViewById<FloatingActionButton>(R.id.floatingActionButton) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val alarmViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appBar)
        setupBottomDrawer()
        fab.setOnClickListener {
            alarmViewModel.showTimePicker()
        }

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Snackbar.make(coordinatorLayout, item?.title ?: "", Snackbar.LENGTH_LONG).show()
        return true
    }

    private fun setupBottomDrawer() {
        val bottomDrawer = findViewById<FrameLayout>(R.id.bottom_drawer)
        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        appBar.setNavigationOnClickListener{
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    /**
     * Handle Listeners
     */
    private val mOnNavigationItemSelectedListener = com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_alarm -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
