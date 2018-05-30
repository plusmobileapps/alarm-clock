package com.plusmobileapps.clock.alarm.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.alarm.landing.EXTRA_ALARM_ID
import com.plusmobileapps.clock.data.entities.Alarm
import com.plusmobileapps.clock.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_alarm_detail.*
import javax.inject.Inject

class AlarmDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AlarmLandingViewModel
    private lateinit var alarm: LiveData<Alarm>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_detail)
        MyApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java)
        val id = intent.extras[EXTRA_ALARM_ID] as Int
        val alarmObserver = Observer<Alarm> {
            if(it != null) {
                alarm_time.text = it.printTime()
            }
        }
        viewModel.getAlarmById(id).observe(this, alarmObserver)
    }
}