package com.plusmobileapps.clock.alarm

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.plusmobileapps.clock.R

class AlarmFragment : Fragment(), AlarmContract.View {

    var mPresenter: AlarmContract.Presenter? = null

    override fun setPresenter(presenter: AlarmContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun showTimePicker() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAlarms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }
}