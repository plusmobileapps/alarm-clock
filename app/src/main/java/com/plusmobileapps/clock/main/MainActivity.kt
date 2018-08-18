package com.plusmobileapps.clock.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmFragment
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.stopwatch.StopwatchFragment
import com.plusmobileapps.clock.timer.landing.TimerFragment
import com.plusmobileapps.clock.timer.landing.TimerViewModel
import com.plusmobileapps.clock.timer.picker.TimerPickerViewModel
import javax.inject.Inject

class MainActivity() : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private val coordinatorLayout by lazy { findViewById<CoordinatorLayout>(R.id.coordinator) }
//    private val appBar by lazy { findViewById<BottomAppBar>(R.id.bottomAppBar) }
//    private val fab by lazy { findViewById<FloatingActionButton>(R.id.floatingActionButton) }
//    private val signOnButton by lazy { findViewById<Button>(R.id.sign_on_button) }
//    private val signOffButton by lazy { findViewById<Button>(R.id.sign_out_button) }
//    private val profileImage by lazy { findViewById<ImageView>(R.id.profile_image) }

    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var firebaseAuthHelper: FirebaseAuthHelper

    private val alarmViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java) }
    private val timerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TimerViewModel::class.java) }
    private val timerPickerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TimerPickerViewModel::class.java) }
    private val mainActivityViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }
    private val navController by lazy { Navigation.findNavController(findViewById(R.id.nav_host_fragment)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        findViewById<BottomNavigationView>(R.id.navigation_view).apply {
            setOnNavigationItemReselectedListener(this@MainActivity)
            setOnNavigationItemSelectedListener(this@MainActivity)
        }
//        setSupportActionBar(appBar)

        if (FirebaseAuth.getInstance().currentUser != null) setupAuthenticatedState() else setupUnauthenticatedState()

        mainActivityViewModel.killApp.observe(this, Observer { finish() })
    }

    private fun setupAuthenticatedState() {
//        profileImage.showOrGone(true)
//        signOffButton.showOrGone(true)
//        signOnButton.showOrGone(false)
//        FirebaseAuth.getInstance().currentUser?.photoUrl?.let {
//            Picasso.get()
//                    .load(it)
//                    .transform(CircleTransform())
//                    .into(profileImage)
//        }
    }

    private fun setupUnauthenticatedState() {
//        profileImage.showOrGone(false)
//        signOffButton.showOrGone(false)
//        signOnButton.showOrGone(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Snackbar.make(coordinatorLayout, item?.title ?: "", Snackbar.LENGTH_LONG).show()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val authenticated = firebaseAuthHelper.handleResult(requestCode, resultCode, data)
        if (authenticated) setupAuthenticatedState() else setupUnauthenticatedState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        return when (item.itemId) {
            R.id.navigation_alarm -> {
                navController.navigate(R.id.alarmFragment)
                true
            }
            R.id.navigation_timer -> {
                navController.navigate(R.id.timerFragment)
                true
            }
            R.id.navigation_stopwatch -> {
                navController.navigate(R.id.stopwatchFragment)
                true
            }
            else -> false
        }
    }

    override fun onNavigationItemReselected(p0: MenuItem) {
    }

    /**
     * Handle Listeners
     */
    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_alarm -> {
                navController.navigate(R.id.alarmFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {
                navController.navigate(R.id.timerFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                navController.navigate(R.id.stopwatchFragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    fun setTimerNumberClicked(view: View) {
        if (view is Button) {
            timerPickerViewModel.onNumberClicked(view.text.toString())
        }
    }

    override fun onBackPressed() {
        mainActivityViewModel.onBackKeyPressed()
    }
}
