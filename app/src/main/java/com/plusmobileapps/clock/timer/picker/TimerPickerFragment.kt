package com.plusmobileapps.clock.timer.picker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import javax.inject.Inject

class TimerPickerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerPickerFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TimerPickerViewModel

    private val secondsText by lazy { view?.findViewById<TextView>(R.id.label_seconds) }
    private val minutesText by lazy { view?.findViewById<TextView>(R.id.label_minutes) }
    private val hoursText by lazy { view?.findViewById<TextView>(R.id.label_hours) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerPickerViewModel::class.java)
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.timer_picker_dialog, container, false)
    }

    private fun subscribe() {
        viewModel.getSeconds().observe(this, Observer {
            secondsText?.text = viewModel.getDisplayTime(it)
        })
        viewModel.getMinutes().observe(this, Observer {
            minutesText?.text = viewModel.getDisplayTime(it)
        })
        viewModel.getHours().observe(this, Observer {
            hoursText?.text = viewModel.getDisplayTime(it)
        })
        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            viewModel.onTimerStartedFabClick()
        }
        view?.findViewById<ImageButton>(R.id.delete_button)?.setOnClickListener {
            viewModel.onDeleteClicked()
        }
    }


}
