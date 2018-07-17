package com.plusmobileapps.clock.alarm.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.alarm.landing.EXTRA_ALARM_ID
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.di.ViewModelFactory
import javax.inject.Inject

class AlarmDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AlarmLandingViewModel
    private lateinit var alarm: LiveData<Alarm>
    private val alarmTime by lazy {
        findViewById<TextView>(R.id.alarm_time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_detail)
        MyApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java)
        val id = intent.extras[EXTRA_ALARM_ID] as Int
        val alarmObserver = Observer<Alarm> {
            if(it != null) {
                alarmTime.text = it.printTime()
            }
        }
        viewModel.getAlarmById(id).observe(this, alarmObserver)
    }
}
