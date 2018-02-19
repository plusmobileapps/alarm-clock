package com.plusmobileapps.clock.main

import android.support.v4.view.ViewPager
import com.plusmobileapps.clock.FragmentProvider
import com.plusmobileapps.clock.R

enum class ViewPagerState {
    ALARM, CREATE_TIMER, IN_PROGRESS_TIMER, STOPWATCH
}

class MainActivityPresenter(private val view: MainActivityContract.View) : MainActivityContract.Presenter {

    var state = ViewPagerState.ALARM

    override fun onPageScrollStateChanged(state: Int) {
        when (state) {
            ViewPager.SCROLL_STATE_IDLE -> showButtons()
            ViewPager.SCROLL_STATE_DRAGGING -> disableButtons()
        }
    }

    private fun disableButtons() {
        view.showButtons(false)
    }

    private fun showButtons() {
        when (state) {
            ViewPagerState.ALARM -> view.showAlarm()
            ViewPagerState.STOPWATCH -> view.showStopWatch()
            ViewPagerState.CREATE_TIMER -> view.showCreateTimer()
            ViewPagerState.IN_PROGRESS_TIMER -> view.showTimerInProgress()
        }
    }


    override fun start() {
        //TODO: Load the data
    }

    override fun navButtonClicked(position: Int) {
        updateViewpagerState(position)
        view.changePage(position)
    }

    override fun pageSwiped(position: Int) {
        updateViewpagerState(position)
        when(position) {
            0 -> view.changeNavHighlight(R.id.navigation_alarm)
            1 -> view.changeNavHighlight(R.id.navigation_timer)
            2 -> view.changeNavHighlight(R.id.navigation_stopwatch)
        }
    }

    override fun backButtonPressed() {
        view.finishActivity()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFabClicked() {
        when(state) {
            ViewPagerState.ALARM -> FragmentProvider.alarmFragmentInstance().fabClicked()
        }
    }

    override fun onAddTimerClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteTimerClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateViewpagerState(position: Int) {
        state = when(position) {
            0 -> ViewPagerState.ALARM
            1 -> ViewPagerState.CREATE_TIMER
            2 -> ViewPagerState.STOPWATCH
            else -> ViewPagerState.ALARM
        }
    }
}