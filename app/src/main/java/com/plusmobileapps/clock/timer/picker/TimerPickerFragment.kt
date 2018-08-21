package com.plusmobileapps.clock.timer.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import javax.inject.Inject

class TimerPickerFragment : Fragment(), OnReselectedDelegate {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.findNavController()?.navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() = setupActionBar("Timer Picker", true)

    override fun onReselected() = setupActionBar()
}
