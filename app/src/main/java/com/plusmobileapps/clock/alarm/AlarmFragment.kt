package com.plusmobileapps.clock.alarm

import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_alarm.*

import com.plusmobileapps.clock.R
import java.util.*

class AlarmFragment : Fragment(), AlarmContract.View {

    var mPresenter: AlarmContract.Presenter? = null

    override fun setPresenter(presenter: AlarmContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_alarm, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.start()
        addAlarmButton.setOnClickListener { mPresenter?.createAlarmClicked() }
    }

    override fun showTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(context, timePickerListener, hour, minute,true)
        dialog.show()
    }

    override fun showAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, min: Int ->
        mPresenter?.timePicked(hour, min)
    }

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }
}