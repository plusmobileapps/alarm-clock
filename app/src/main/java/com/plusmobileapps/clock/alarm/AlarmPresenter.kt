package com.plusmobileapps.clock.alarm

class AlarmPresenter(private val view: AlarmContract.View): AlarmContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun start() {

    }

    override fun createAlarmClicked() = view.showTimePicker()

    override fun timePicked(hour: Int, min: Int) {

    }

    override fun alarmDeleteClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun alarmClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButtonPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}