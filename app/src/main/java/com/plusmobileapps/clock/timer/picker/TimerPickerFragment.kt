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

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TimerPickerViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.timer_picker_dialog, container, false)
        subscribe(view)
        return view
    }

    private fun subscribe(view: View) {
        viewModel.getSeconds().observe(this, Observer {
            view.findViewById<TextView>(R.id.label_seconds).text = it?.toString() ?: "00"
        })
        viewModel.getMinutes().observe(this, Observer {
            minutesText?.text = it?.toString()
        })
        viewModel.getHours().observe(this, Observer {
            hoursText?.text = it?.toString()
        })
        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            viewModel.onTimerStartedFabClick()
        }
        view?.findViewById<ImageButton>(R.id.delete_button)?.setOnClickListener {
            viewModel.onDeleteClicked()
        }
    }

    companion object {
        fun newInstance() = TimerPickerFragment()
    }
}
