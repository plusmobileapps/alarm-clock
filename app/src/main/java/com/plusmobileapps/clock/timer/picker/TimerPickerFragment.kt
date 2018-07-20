package com.plusmobileapps.clock.timer.picker

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.plusmobileapps.clock.R

class TimerPickerFragment : Fragment() {

    interface TimerPickerDialogListener {
        fun onTimerStarted(seconds: Int, minutes: Int, hours: Int)
    }

    private lateinit var listener: TimerPickerDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.timer_picker_dialog, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as TimerPickerDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement TimerPickerDialogListener")
        }
    }

    companion object {
        fun newInstance() = TimerPickerFragment()
    }
}
