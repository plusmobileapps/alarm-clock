package com.plusmobileapps.clock.main

class MainActivityPresenter(private val view: MainActivityContract.View): MainActivityContract.Presenter {


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