package com.plusmobileapps.clock.main

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.util.or
import com.plusmobileapps.clock.util.showOrInvisible
import javax.inject.Inject

class MainActivity() : AppCompatActivity() {

    private val root by lazy { findViewById<ConstraintLayout>(R.id.container) }
    private val alarmSectionWrapper by lazy { findViewById<FrameLayout>(R.id.alarm_section_wrapper) }
    private val timerSectionWrapper by lazy { findViewById<FrameLayout>(R.id.timer_section_wrapper) }
    private val stopwatchSectionWrapper by lazy { findViewById<FrameLayout>(R.id.stopwatch_section_wrapper) }

    private val alarmNavController: NavController by lazy { findNavController(R.id.nav_host_alarm) }
    private val timerNavController: NavController by lazy { findNavController(R.id.nav_host_timer) }
    private val stopwatchNavController: NavController by lazy { findNavController(R.id.nav_host_stopwatch) }

    private var currentController: NavController? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainActivityViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var returnValue = false

        when (item.itemId) {
            R.id.navigation_alarm -> {
                currentController = alarmNavController
                alarmSectionWrapper.showOrInvisible(true)
                timerSectionWrapper.showOrInvisible(false)
                stopwatchSectionWrapper.showOrInvisible(false)
                returnValue = true
                onReselected(R.id.nav_host_alarm)
            }
            R.id.navigation_timer -> {
                currentController = timerNavController
                alarmSectionWrapper.showOrInvisible(false)
                timerSectionWrapper.showOrInvisible(true)
                stopwatchSectionWrapper.showOrInvisible(false)
                returnValue = true
                onReselected(R.id.nav_host_timer)
            }
            R.id.navigation_stopwatch -> {
                currentController = stopwatchNavController
                alarmSectionWrapper.showOrInvisible(false)
                timerSectionWrapper.showOrInvisible(false)
                stopwatchSectionWrapper.showOrInvisible(true)
                returnValue = true
                onReselected(R.id.nav_host_stopwatch)
            }
        }

        return@OnNavigationItemSelectedListener returnValue
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        findViewById<BottomNavigationView>(R.id.navigation_view).apply {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        currentController = alarmNavController
        alarmSectionWrapper.showOrInvisible(true)
        timerSectionWrapper.showOrInvisible(false)
        stopwatchSectionWrapper.showOrInvisible(false)
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }

    override fun onBackPressed() {
        currentController
                ?.let { if(it.popBackStack().not()) finish() }
                .or { finish() }
    }

    private fun onReselected(navHostId: Int) {
        val navFragment = supportFragmentManager.findFragmentById(navHostId) ?: return
        (navFragment.childFragmentManager.fragments.first { it.isVisible } as OnReselectedDelegate).onReselected()
    }
}
