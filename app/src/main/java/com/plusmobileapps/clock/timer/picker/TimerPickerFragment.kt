package com.plusmobileapps.clock.timer.picker

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.TIMER_NOTIFICATION_CHANNEL_ID
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.MainActivity
import com.plusmobileapps.clock.main.MainActivityViewModel
import com.plusmobileapps.clock.timer.landing.TimerFragment
import org.w3c.dom.Text
import javax.inject.Inject

class TimerPickerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerPickerFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var timerViewModel: TimerPickerViewModel
    private lateinit var navController: NavController

    private lateinit var secondsText: TextView
    private lateinit var minutesText: TextView
    private lateinit var hoursText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        timerViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerPickerViewModel::class.java)
        view?.let {
            navController = Navigation.findNavController(it)
            secondsText = it.findViewById(R.id.label_seconds)
            minutesText = it.findViewById(R.id.label_minutes)
            hoursText = it.findViewById(R.id.label_hours)
            it.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                timerViewModel.onTimerStartedFabClick()
            }
            it.findViewById<ImageButton>(R.id.delete_button).setOnClickListener {
                timerViewModel.onDeleteClicked()
            }
            subscribe()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.timer_picker_dialog, container, false)
    }

    private fun subscribe() {
        timerViewModel.getSeconds().observe(this, Observer {
            secondsText.text = timerViewModel.getDisplayTime(it)
        })
        timerViewModel.getMinutes().observe(this, Observer {
            minutesText.text = timerViewModel.getDisplayTime(it)
        })
        timerViewModel.getHours().observe(this, Observer {
            hoursText.text = timerViewModel.getDisplayTime(it)
        })
        timerViewModel.timerButtonStartEvent.observe(this, Observer {
            navController.popBackStack()
        })
        timerViewModel.snackbarError.observe(this, Observer {
            Toast.makeText(context, "Need a number!", Toast.LENGTH_LONG).show()
        })

    }



}
