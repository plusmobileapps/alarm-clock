package com.plusmobileapps.clock.timer.landing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.timer.picker.TimerPickerFragment
import javax.inject.Inject

interface TimerPickerActivityCallback {
    fun onTimerStarted(seconds: Int, minutes: Int, hours: Int)
}

class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()
    }

    private lateinit var mView: View

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TimerViewModel
    private val recyclerView by lazy { mView.findViewById<RecyclerView>(R.id.recycler_view) }

    private val itemListener = object : TimerItemListener {
        override fun timerToggled(position: Int) = viewModel.toggleTimer(position)
        override fun timerReset(position: Int) = viewModel.resetTimer(position)
        override fun timerItemDeleted(position: Int) = viewModel.deleteTimer(position)
    }

    private val timerAdapter = TimerAdapter(itemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TimerViewModel::class.java)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = timerAdapter
        }

        subscribeToTimerList()
        subscribeToAddTimerClick()
    }

    private fun subscribeToTimerList() {
        viewModel.timers.observe(this, Observer {
            it?.let {
                timerAdapter.submitList(it)
            }
        })
    }

    private fun subscribeToAddTimerClick() {
        viewModel.timerClicked.observe(this, Observer {
            fragmentManager?.transaction {
                replace(R.id.fragment_container, TimerPickerFragment.newInstance())
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        mView = view
        return view
    }

}