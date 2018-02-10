package com.plusmobileapps.clock.main

import com.plusmobileapps.clock.BasePresenter
import com.plusmobileapps.clock.BaseView

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
    }

}