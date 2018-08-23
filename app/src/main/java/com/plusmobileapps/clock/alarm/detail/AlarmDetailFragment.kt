package com.plusmobileapps.clock.alarm.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.landing.AlarmLandingViewModel
import com.plusmobileapps.clock.data.alarm.Alarm
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import javax.inject.Inject

class AlarmDetailFragment : Fragment(), OnReselectedDelegate {

    companion object {
        const val EXTRA_ALARM_ID = "alarm_id"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AlarmLandingViewModel
    private lateinit var alarm: LiveData<Alarm>
    private val alarmTime by lazy { view?.findViewById<TextView>(R.id.alarm_time) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlarmLandingViewModel::class.java)
        val id = arguments?.getInt(EXTRA_ALARM_ID) ?: return
        viewModel.getAlarmById(id).observe(this, Observer {
            alarmTime?.text = it.printTime()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_alarm_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    override fun onReselected() = setupActionBar()

    private fun setupActionBar() = setupActionBar("Alarm Detail", true)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.findNavController()?.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}