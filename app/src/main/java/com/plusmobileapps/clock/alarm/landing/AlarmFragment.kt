package com.plusmobileapps.clock.alarm.landing

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.detail.AlarmDetailActivity
import com.plusmobileapps.clock.data.entities.Alarm
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.util.*
import javax.inject.Inject

const val EXTRA_ALARM_ID = "alarm_id"

class AlarmFragment : Fragment(){

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    @Inject
    lateinit var viewModel: AlarmLandingViewModel

    private val itemListener = object : AlarmItemListener {
        override fun alarmItemClicked(position: Int) {
            val id = viewModel.getAlarmId(position)
            id?.let {
                val intent = Intent(context, AlarmDetailActivity::class.java)
                intent.putExtra(EXTRA_ALARM_ID, it)
                startActivity(intent)
            }
        }

        override fun alarmTimeClicked(position: Int) {
            val alarm = viewModel.getAlarm(position)
            alarm?.let {
                showTimePicker(it.hour, it.min, TimePickerDialog.OnTimeSetListener {  _: TimePicker, hour: Int, min: Int ->
                    it.hour = hour
                    it.min = min
                    viewModel.updateAlarm(it)
                })
            }
        }

        override fun alarmSwitchToggled(position: Int, isEnabled: Boolean) {
            viewModel.updateAlarmToggle(isEnabled, position)
        }
    }
    private val alarmAdapter by lazy {
        AlarmAdapter(listOf(), itemListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_alarm_button.setOnClickListener { showTimePicker(null, null, addAlarmTimeListener) }
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

    private fun showTimePicker(startHour: Int?, startMin: Int?, timeListener: TimePickerDialog.OnTimeSetListener) {
        val c = Calendar.getInstance()
        val hour = startHour ?: c.get(Calendar.HOUR_OF_DAY)
        val minute = startMin ?: c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, timeListener, hour, minute,true)
        dialog.show()
    }

    private val addAlarmTimeListener = TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, min: Int ->
        viewModel.addAlarm(hour, min)
    }


}