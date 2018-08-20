package com.plusmobileapps.clock.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.util.showOrGone
import javax.inject.Inject

class MainActivity() : AppCompatActivity() {

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

    private val mainActivityViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }
    private val navController by lazy { Navigation.findNavController(findViewById(R.id.nav_host_fragment)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(toolbar, navController)

        if (FirebaseAuth.getInstance().currentUser != null) setupAuthenticatedState() else setupUnauthenticatedState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.topappbar_menu, menu)
        navController.addOnNavigatedListener { controller, destination ->
            val menuButton = menu?.findItem(R.id.menu_settings)

            when (destination.id) {
                R.id.timerPickerFragment, R.id.alarmDetailFragment -> {
                    menuButton?.isVisible = false
                }
                R.id.navigation_timer, R.id.navigation_alarm, R.id.navigation_stopwatch -> {
                    menuButton?.isVisible = true
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
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
        if (item?.itemId == R.id.menu_settings) {
            navController.navigate(R.id.settingsFragment)
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val authenticated = firebaseAuthHelper.handleResult(requestCode, resultCode, data)
        if (authenticated) setupAuthenticatedState() else setupUnauthenticatedState()
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean{
//        return when (item.itemId) {
//            R.id.navigation_alarm -> {
//                navController.navigate(R.id.alarmFragment)
//                true
//            }
//            R.id.navigation_timer -> {
//                navController.navigate(R.id.timerFragment)
//                true
//            }
//            R.id.navigation_stopwatch -> {
//                navController.navigate(R.id.stopwatchFragment)
//                true
//            }
//            else -> false
//        }
//    }
//
//    override fun onNavigationItemReselected(item: MenuItem) {
//        when (item.itemId) {
//            R.id.navigation_alarm -> navController.navigate(R.id.alarmFragment)
//            R.id.navigation_timer -> navController.navigate(R.id.timerFragment)
//            R.id.navigation_stopwatch -> navController.navigate(R.id.stopwatchFragment)
//            else -> Unit
//        }
//    }



    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}
