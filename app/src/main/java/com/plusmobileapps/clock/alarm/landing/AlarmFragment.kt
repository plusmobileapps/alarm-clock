package com.plusmobileapps.clock.alarm.landing

import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SimpleAdapter
import android.widget.TimePicker
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.FirebaseAuthHelper
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.alarm.detail.AlarmDetailActivity
import com.plusmobileapps.clock.data.entities.Alarm
import com.plusmobileapps.clock.di.ViewModelFactory
import java.util.*
import javax.inject.Inject

const val EXTRA_ALARM_ID = "alarm_id"

class AlarmFragment : androidx.fragment.app.Fragment(){

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    private val recyclerView by lazy {
        mView.findViewById<RecyclerView>(R.id.recycler_view)
    }

    private lateinit var mView: View

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: AlarmLandingViewModel

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
        AlarmAdapter(itemListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(AlarmLandingViewModel::class.java)
        recyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = alarmAdapter
        }
        val swipeHandler = object : SwipeToDeleteCallback(mView.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is AlarmAdapter.AlarmViewHolder) viewModel.deleteAlarm(viewHolder.mAlarm)
            }
        }
        ItemTouchHelper(swipeHandler).apply {
            attachToRecyclerView(recyclerView)
        }
        subscribeToAlarmList()
        subscribeToShowingTimePicker()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        mView = view
        return view
    }

    private fun subscribeToAlarmList() {
        val alarmsObserver = Observer<List<Alarm>>() {
            if(it != null) alarmAdapter.submitList(it)
        }
        viewModel.getAlarms().observe(this, alarmsObserver)
    }

    private fun subscribeToShowingTimePicker() {
        viewModel.showTimePickerToggle.observe(this, Observer {
            showTimePicker(null, null, addAlarmTimeListener)
        })
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