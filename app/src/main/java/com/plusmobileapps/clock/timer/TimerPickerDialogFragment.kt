package com.plusmobileapps.clock.timer

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.plusmobileapps.clock.R

class TimerPickerDialogFragment : DialogFragment() {

    interface TimerPickerDialogListener {
        fun onTimerStarted(seconds: Int, minutes: Int, hours: Int)
    }

    companion object {
        const val TAG = "TimerPickerDialogFragment"
    }

    private lateinit var listener: TimerPickerDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        inflater?.let {
            builder.setView(it.inflate(R.layout.timer_picker_dialog, null))
        }
        return builder.create()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as TimerPickerDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement TimerPickerDialogListener")
        }
    }
}