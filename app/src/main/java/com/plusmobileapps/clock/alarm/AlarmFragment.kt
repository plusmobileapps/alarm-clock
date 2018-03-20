package com.plusmobileapps.clock.alarm

import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.plusmobileapps.clock.R
import java.util.*

class AlarmFragment : Fragment(){

    lateinit var viewModel: AlarmViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

     fun showTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, timePickerListener, hour, minute,true)
        dialog.show()
    }

    fun fabClicked() {

    }

     fun showAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, min: Int ->
    }

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }
}