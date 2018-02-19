package com.plusmobileapps.clock.main

interface MainActivityContract {

    interface View {
        fun changePage(position: Int)
        fun changeNavHighlight(position: Int)
        fun showButtons(show: Boolean)
        fun showAlarm()
        fun showCreateTimer()
        fun showTimerInProgress()
        fun showStopWatch()
        fun finishActivity()
    }

    interface Presenter {
        fun start()
        fun navButtonClicked(position: Int)
        fun pageSwiped(position: Int)
        fun backButtonPressed()
        fun onPageScrollStateChanged(state: Int)
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
        fun onFabClicked()
        fun onAddTimerClicked()
        fun onDeleteTimerClicked()
    }

}