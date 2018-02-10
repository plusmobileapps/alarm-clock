package com.plusmobileapps.clock.alarm

import com.plusmobileapps.clock.BasePresenter
import com.plusmobileapps.clock.BaseView

interface AlarmContract {

    interface View: BaseView<Presenter> {
        fun showTimePicker()
        fun showAlarms()
    }

    interface Presenter: BasePresenter {
        fun createAlarmClicked()
        fun timePicked(hour: Int, min: Int)
        fun alarmDeleteClicked()
        fun alarmClicked(position: Int)
        fun backButtonPressed()
    }

}