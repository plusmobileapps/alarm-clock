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
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmFragment
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.stopwatch.StopwatchFragment
import com.plusmobileapps.clock.timer.landing.TimerFragment
import com.plusmobileapps.clock.timer.landing.TimerViewModel
import com.plusmobileapps.clock.timer.picker.TimerPickerViewModel
import com.plusmobileapps.clock.util.CircleTransform
import com.plusmobileapps.clock.util.requiresGooglePlayServices
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

    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val alarmViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java) }
    private val timerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TimerViewModel::class.java) }
    private val timerPickerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TimerPickerViewModel::class.java) }
    private val mainActivityViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appBar)
        setupBottomDrawer()

        mainActivityViewModel.getViewStateLiveData().observe(this, Observer {
            when(it) {
                MainActivityViewState.Alarm -> fab.setImageResource(R.drawable.ic_add_white_24px)
                MainActivityViewState.Timer -> fab.setImageResource(R.drawable.ic_add_white_24px)
                MainActivityViewState.TimerPicker -> fab.setImageResource(R.drawable.ic_play_arrow_white_24px)
                MainActivityViewState.StopWatch -> fab.setImageResource(R.drawable.ic_play_arrow_white_24px)
            }
        })

        fab.setOnClickListener {
            val viewState = mainActivityViewModel.getViewStateLiveData().value
            when(viewState) {
                MainActivityViewState.Alarm -> alarmViewModel.showTimePicker()
                MainActivityViewState.Timer -> {
                    timerViewModel.timerAddClicked()
                    mainActivityViewModel.addTimerClicked()
                }
                MainActivityViewState.TimerPicker -> timerPickerViewModel.onTimerStartedFabClick()
                else -> Unit
            }

        }
        signOnButton.setOnClickListener {
            requiresGooglePlayServices(this) {
                alarmViewModel.firebaseAuthHelper.startAuth(this)
            }
        }

        signOffButton.setOnClickListener {
            alarmViewModel.firebaseAuthHelper.signOut(this).observe(this, Observer {
                toast(getString(R.string.sign_out_successful))
                setupUnauthenticatedState()
            })
        }
        if (FirebaseAuth.getInstance().currentUser != null) setupAuthenticatedState() else setupUnauthenticatedState()

        findViewById<NavigationView>(R.id.navigation_view).setNavigationItemSelectedListener {
            mainActivityViewModel.navigationClicked(it.itemId)
        }

        mainActivityViewModel.getViewStateLiveData().observe(this, Observer {
            it?.let {
                val fragment = when(it) {
                    MainActivityViewState.Alarm -> AlarmFragment.newInstance()
                    MainActivityViewState.StopWatch -> StopwatchFragment.newInstance()
                    MainActivityViewState.Timer -> TimerFragment.newInstance()
                    else -> null
                }
                fragment?.let {
                    supportFragmentManager.transaction {
                        replace(R.id.fragment_container, fragment)
                    }
                }
            }
        })

        mainActivityViewModel.closeBottomDrawer.observe(this, Observer {
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        })

        mainActivityViewModel.killApp.observe(this, Observer { finish() })
    }

    private fun setupAuthenticatedState() {
        profileImage.showOrGone(true)
        signOffButton.showOrGone(true)
        signOnButton.showOrGone(false)
        FirebaseAuth.getInstance().currentUser?.photoUrl?.let {
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
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                supportFragmentManager.transaction {
                    replace(R.id.fragment_container, StopwatchFragment.newInstance())
                }
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
