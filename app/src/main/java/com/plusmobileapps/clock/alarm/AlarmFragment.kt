package com.plusmobileapps.clock.alarm

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.data.entities.Alarm
import com.plusmobileapps.clock.di.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment(){

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: AlarmViewModel
    private val alarmAdapter by lazy {
        AlarmAdapter(listOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlarmViewModel::class.java)
        add_alarm_button.setOnClickListener { showTimePicker() }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = alarmAdapter
        }
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_alarm, container, false)
    }

    private fun subscribe() {
        val alarmsObserver = Observer<List<Alarm>>() {
            if(it != null){
                alarmAdapter.apply {
                    alarms = it
                    notifyDataSetChanged()
                }
            }
        }
        viewModel.getAlarms().observe(this, alarmsObserver)
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, timePickerListener, hour, minute,true)
        dialog.show()
    }

    private val timePickerListener = TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, min: Int ->
        viewModel.addAlarm(hour, min)
    }
}