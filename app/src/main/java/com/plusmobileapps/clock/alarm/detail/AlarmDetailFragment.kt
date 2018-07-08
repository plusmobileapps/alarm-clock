package com.plusmobileapps.clock.alarm.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.alarm.landing.EXTRA_ALARM_ID
import com.plusmobileapps.clock.di.ViewModelFactory
import javax.inject.Inject

class AlarmDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AlarmLandingViewModel
    private val alarmTime by lazy { view?.findViewById<TextView>(R.id.alarm_time) }


    private var alarmId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java)
        arguments?.let {
            alarmId = it.getInt(EXTRA_ALARM_ID)
            viewModel.getAlarmById(id).observe(this, Observer {
                if(it != null) {
                    alarmTime?.text = it.printTime()
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_alarm_detail, container, false)
    }



    companion object {
        @JvmStatic
        fun newInstance(alarmId: Int) =
                AlarmDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(EXTRA_ALARM_ID, alarmId)
                    }
                }
    }
}
