package com.plusmobileapps.clock.main

interface MainActivityContract {

    interface View {
        fun changePage(position: Int)
        fun changeNavHighlight(position: Int)
        fun finishActivity()
    }

    interface Presenter {
        fun start()
        fun navButtonClicked(position: Int)
        fun pageSwiped(position: Int)
        fun backButtonPressed()
        fun onPageScrollStateChanged(state: Int)
    }

}