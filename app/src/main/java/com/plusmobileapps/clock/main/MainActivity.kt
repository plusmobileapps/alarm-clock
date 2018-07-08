package com.plusmobileapps.clock.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.util.CircleTransform
import com.plusmobileapps.clock.util.showOrGone
import com.squareup.picasso.Picasso
import org.jetbrains.anko.toast
import javax.inject.Inject

enum class BottomNav {
    ALARM, TIMER, STOPWATCH
}

class MainActivity() : AppCompatActivity() {

    private val coordinatorLayout by lazy { findViewById<CoordinatorLayout>(R.id.coordinator) }
    private val appBar by lazy { findViewById<BottomAppBar>(R.id.bottomAppBar) }
    private val fab by lazy { findViewById<FloatingActionButton>(R.id.floatingActionButton) }
    private val signOnButton by lazy { findViewById<Button>(R.id.sign_on_button) }
    private val signOffButton by lazy { findViewById<Button>(R.id.sign_out_button) }
    private val profileImage by lazy { findViewById<ImageView>(R.id.profile_image) }
    private val navigationView by lazy { findViewById<NavigationView>(R.id.navigation_view) }
    private val navigator by lazy { findNavController(R.id.nav_host_fragment) }


    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val alarmViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appBar)
        setupBottomDrawer()
        fab.setOnClickListener { alarmViewModel.showTimePicker() }
        signOnButton.setOnClickListener { alarmViewModel.firebaseAuthHelper.startAuth(this) }

        signOffButton.setOnClickListener {
            alarmViewModel.firebaseAuthHelper.signOut(this).observe(this, Observer {
                toast(getString(R.string.sign_out_successful))
                setupUnauthenticatedState()
            })
        }
        if (FirebaseAuth.getInstance().currentUser != null) setupAuthenticatedState() else setupUnauthenticatedState()
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setupAuthenticatedState() {
        profileImage.showOrGone(true)
        signOffButton.showOrGone(true)
        signOnButton.showOrGone(false)
        val photoUrl = FirebaseAuth.getInstance().currentUser?.photoUrl
        photoUrl?.let {
            Picasso.get()
                    .load(it)
                    .transform(CircleTransform())
                    .into(profileImage)
        }
    }

    private fun setupUnauthenticatedState() {
        profileImage.showOrGone(false)
        signOffButton.showOrGone(false)
        signOnButton.showOrGone(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Snackbar.make(coordinatorLayout, item?.title ?: "", Snackbar.LENGTH_LONG).show()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val authenticated = alarmViewModel.firebaseAuthHelper.handleResult(requestCode, resultCode, data)
        if (authenticated) setupAuthenticatedState() else setupUnauthenticatedState()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun setupBottomDrawer() {
        val bottomDrawer = findViewById<ConstraintLayout>(R.id.constraint_layout)
        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        appBar.setNavigationOnClickListener{
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    /**
     * Handle Listeners
     */
    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_alarm -> {
                navigator.navigate(R.id.alarmFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {
                navigator.navigate(R.id.timerFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                navigator.navigate(R.id.stopwatchFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
