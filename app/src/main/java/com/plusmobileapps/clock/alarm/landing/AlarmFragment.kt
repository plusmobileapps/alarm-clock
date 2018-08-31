package com.plusmobileapps.clock.alarm.landing

import android.app.TimePickerDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.clock.DataBindingViewHolder
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.alarm.detail.AlarmDetailFragment.Companion.EXTRA_ALARM_ID
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.databinding.FragmentAlarmBinding
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.or
import com.plusmobileapps.clock.util.setupActionBar
import org.jetbrains.anko.bundleOf
import java.util.*
import javax.inject.Inject


class AlarmFragment : Fragment(), OnReselectedDelegate {

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    private lateinit var recyclerView: RecyclerView

    private val navigator by lazy { view?.findNavController() }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var alarmViewModel: AlarmLandingViewModel

    private val alarmAdapter by lazy { AlarmAdapter(alarmViewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyApplication.appComponent.inject(this)
        alarmViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(AlarmLandingViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentAlarmBinding>(inflater, R.layout.fragment_alarm, container, false)
        binding.apply {
            viewmodel = alarmViewModel
            setLifecycleOwner(this@AlarmFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.let {
            recyclerView = it.findViewById<RecyclerView>(R.id.recycler_view).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = alarmAdapter
            }
            val swipeHandler = object : SwipeToDeleteCallback(it.context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vh = viewHolder as DataBindingViewHolder
                    val alarm = vh.data as Alarm
                    alarmViewModel.deleteAlarm(alarm)
                }
            }
            ItemTouchHelper(swipeHandler).apply { attachToRecyclerView(recyclerView) }
            subscribe()
        }
    }

    private fun subscribe() {
        with(alarmViewModel) {
            getAlarms().observe(this@AlarmFragment, Observer {
                alarmAdapter.submitList(it)
            })
            showTimePicker.observe(this@AlarmFragment, Observer {
                it?.let {
                    showTimePicker(startHour = it.hour, startMin = it.min) { hour, min ->
                        val alarm = it.copy(hour = hour, min = min)
                        alarmViewModel.updateAlarm(alarm)
                    }
                }.or { showTimePicker { hour, min -> alarmViewModel.addAlarm(hour, min) } }
            })
            openAlarm.observe(this@AlarmFragment, Observer {
                val bundle = bundleOf(EXTRA_ALARM_ID to it)
                navigator?.navigate(R.id.action_alarmFragment_to_alarmDetailFragment, bundle)
            })
        }
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

    private fun showTimePicker(startHour: Int? = null, startMin: Int? = null, timePickerCallback: (Int, Int) -> Unit) {
        val c = Calendar.getInstance()
        val hour = startHour ?: c.get(Calendar.HOUR_OF_DAY)
        val minute = startMin ?: c.get(Calendar.MINUTE)
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            timePickerCallback(hour, min)
        }
        val dialog = TimePickerDialog(context, timeListener, hour, minute,true)
        dialog.show()
    }

}