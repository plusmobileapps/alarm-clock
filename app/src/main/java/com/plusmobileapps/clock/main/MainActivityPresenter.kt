package com.plusmobileapps.clock.main

import android.net.wifi.WifiConfiguration.Status.ENABLED
import android.support.v4.view.ViewPager

class MainActivityPresenter(private val view: MainActivityContract.View): MainActivityContract.Presenter {

    override fun onPageScrollStateChanged(state: Int) {
        when (state) {
            ViewPager.SCROLL_STATE_IDLE -> setFabState(ENABLED)
            ViewPager.SCROLL_STATE_DRAGGING -> TODO()
            ViewPager.SCROLL_STATE_SETTLING -> TODO()
        }
    }

    private fun setFabState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        //TODO: Load the data
    }

    override fun navButtonClicked(position: Int) {
        view.changePage(position)
    }

    override fun pageSwiped(position: Int) {
        view.changeNavHighlight(position)
    }

    override fun backButtonPressed() {
        view.finishActivity()
    }
}