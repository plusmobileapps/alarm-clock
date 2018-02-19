package com.plusmobileapps.clock.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.plusmobileapps.clock.FragmentProvider
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.AlarmPresenter
import com.plusmobileapps.clock.util.showOrInvisible
import kotlinx.android.synthetic.main.activity_main.*

enum class BottomNav {
    ALARM, TIMER, STOPWATCH
}

class MainActivity() : AppCompatActivity(), MainActivityContract.View {

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
        fab.setOnClickListener { presenter.onFabClicked() }
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
        viewPager.currentItem = position
    }

    override fun changeNavHighlight(itemId: Int) {
        navigation.selectedItemId = itemId
    }

    override fun showButtons(show: Boolean) {
        fab.showOrInvisible(show)
        addTimerButton.showOrInvisible(show)
        timerDeleteButton.showOrInvisible(show)
    }

    override fun showAlarm() {
        fab.showOrInvisible(true)
        fab.setImageDrawable(getDrawable(R.drawable.ic_alarm_add_white_24px))
    }

    override fun showCreateTimer() {
        fab.showOrInvisible(true)
        fab.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_white_24px))
    }

    override fun showTimerInProgress() {
        fab.showOrInvisible(true)
        fab.setImageDrawable(getDrawable(R.drawable.ic_pause_white_24px))
        addTimerButton.showOrInvisible(true)
        timerDeleteButton.showOrInvisible(true)
    }

    override fun showStopWatch() {
        fab.showOrInvisible(true)
        fab.setImageDrawable(getDrawable(R.drawable.ic_stopwatch_white_24px))
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
