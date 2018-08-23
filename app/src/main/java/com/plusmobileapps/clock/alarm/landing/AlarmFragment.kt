package com.plusmobileapps.clock.alarm.landing

import android.app.TimePickerDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.alarm.detail.AlarmDetailFragment.Companion.EXTRA_ALARM_ID
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import org.jetbrains.anko.bundleOf
import java.util.*
import javax.inject.Inject


class AlarmFragment : Fragment(), AlarmItemListener, OnReselectedDelegate {

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView

    private val navigator by lazy { view?.findNavController() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: AlarmLandingViewModel

    private val alarmAdapter = AlarmAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(AlarmLandingViewModel::class.java)
        view?.let {
            recyclerView = it.findViewById<RecyclerView>(R.id.recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = alarmAdapter
            }
            val swipeHandler = object : SwipeToDeleteCallback(it.context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (viewHolder is AlarmAdapter.AlarmViewHolder) viewModel.deleteAlarm(viewHolder.mAlarm)
                }
            }
            ItemTouchHelper(swipeHandler).apply { attachToRecyclerView(recyclerView) }

            it.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                viewModel.showTimePicker()
            }
            subscribeToAlarmList()
            subscribeToShowingTimePicker()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.findNavController()?.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onReselected() = setupActionBar()

    private fun setupActionBar() = setupActionBar(context?.getString(R.string.title_alarm) ?: "Alarm")

    private fun subscribeToAlarmList() = viewModel.getAlarms().observe(this, Observer {
                alarmAdapter.submitList(it)
        })


    private fun subscribeToShowingTimePicker() {
        viewModel.showTimePickerToggle.observe(this, Observer {
            showTimePicker(timeListener = addAlarmTimeListener)
        })
    }

    private fun showTimePicker(startHour: Int? = null, startMin: Int? = null, timeListener: TimePickerDialog.OnTimeSetListener) {
        val c = Calendar.getInstance()
        val hour = startHour ?: c.get(Calendar.HOUR_OF_DAY)
        val minute = startMin ?: c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, timeListener, hour, minute,true)
        dialog.show()
    }

    private val addAlarmTimeListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
        viewModel.addAlarm(hour, min)
    }

    override fun alarmItemClicked(position: Int) {
        val id = viewModel.getAlarmId(position) ?: return
        val bundle = bundleOf(EXTRA_ALARM_ID to id)
        Navigation.createNavigateOnClickListener(R.id.action_alarmFragment_to_alarmDetailFragment, bundle)
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