package com.plusmobileapps.clock.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.util.or
import com.plusmobileapps.clock.util.showOrGone
import com.plusmobileapps.clock.util.showOrInvisible
import org.jetbrains.anko.find
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
    @Inject
    lateinit var firebaseAuthHelper: FirebaseAuthHelper

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
            }
            R.id.navigation_timer -> {
                currentController = timerNavController
                alarmSectionWrapper.showOrInvisible(false)
                timerSectionWrapper.showOrInvisible(true)
                stopwatchSectionWrapper.showOrInvisible(false)
                returnValue = true
            }
            R.id.navigation_stopwatch -> {
                currentController = stopwatchNavController
                alarmSectionWrapper.showOrInvisible(false)
                timerSectionWrapper.showOrInvisible(false)
                stopwatchSectionWrapper.showOrInvisible(true)
                returnValue = true
            }
        }
        onReselected(item.itemId)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val authenticated = firebaseAuthHelper.handleResult(requestCode, resultCode, data)
//        if (authenticated) setupAuthenticatedState() else setupUnauthenticatedState()
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }

    override fun onBackPressed() {
        currentController
                ?.let { if(it.popBackStack().not()) finish() }
                .or { finish() }
    }

    private fun onReselected(itemId: Int) {
        when (itemId) {
            R.id.navigation_alarm -> {
                val fragmentClassName = (alarmNavController.currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_alarm) ?: return
                navFragment.childFragmentManager.fragments.asReversed().forEach { fragment ->
                    if (fragment.javaClass.simpleName == fragmentClassName && fragment is OnReselectedDelegate) {
                        fragment.onReselected()
                        return@forEach
                    }
                }
            }
            R.id.navigation_timer -> {
                val fragmentClassName = (timerNavController.currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_timer) ?: return
                navFragment.childFragmentManager.fragments.asReversed().forEach { fragment ->
                    if (fragment.javaClass.simpleName == fragmentClassName && fragment is OnReselectedDelegate) {
                        fragment.onReselected()
                        return@forEach
                    }
                }
            }
            R.id.navigation_stopwatch -> {
                val fragmentClassName = (stopwatchNavController.currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_stopwatch) ?: return
                navFragment.childFragmentManager.fragments.asReversed().forEach { fragment ->
                    if (fragment.javaClass.simpleName == fragmentClassName && fragment is OnReselectedDelegate) {
                        fragment.onReselected()
                        return@forEach
                    }
                }
            }
        }
    }
}
