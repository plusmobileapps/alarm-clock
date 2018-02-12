package com.plusmobileapps.clock.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.plusmobileapps.clock.FragmentProvider
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.AlarmPresenter

enum class BottomNav {
    ALARM, TIMER, STOPWATCH
}

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private val presenter = MainActivityPresenter(this)

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
        setUpPresenters()
    }

    private fun setUpPresenters() {
        AlarmPresenter(FragmentProvider.alarmFragmentInstance())
        //TODO: Setup timer and stop watch presenters
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun changePage(position: Int) {
        //TODO: wire up the swipe adapter to change page
    }

    override fun changeNavHighlight(position: Int) {
        navigation.selectedItemId = position
    }

    override fun finishActivity() = finish()

    override fun onBackPressed() = presenter.backButtonPressed()

    /**
     * Handle Listeners
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_alarm -> {
                presenter.navButtonClicked(BottomNav.ALARM.ordinal)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_timer -> {
                presenter.navButtonClicked(BottomNav.TIMER.ordinal)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_stopwatch -> {
                presenter.navButtonClicked(BottomNav.STOPWATCH.ordinal)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val pageChangeListener = object:ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            presenter.onPageScrollStateChanged(state)

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) = presenter.pageSwiped(position)
    }
}
