package com.plusmobileapps.clock.timer.landing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.timer.picker.TimerPickerFragment
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import javax.inject.Inject

interface TimerPickerActivityCallback {
    fun onTimerStarted(seconds: Int, minutes: Int, hours: Int)
}

class TimerFragment : Fragment(), OnReselectedDelegate {

    companion object {
        fun newInstance() = TimerFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var timerViewModel: TimerViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navigator: NavController

    private val itemListener = object : TimerItemListener {
        override fun timerToggled(position: Int) = timerViewModel.toggleTimer(position)
        override fun timerReset(position: Int) = timerViewModel.resetTimer(position)
        override fun timerItemDeleted(position: Int) = timerViewModel.deleteTimer(position)
    }

    private val timerAdapter = TimerAdapter(itemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        timerViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerViewModel::class.java)
        view?.let {
            navigator = Navigation.findNavController(it)
            recyclerView = it.findViewById(R.id.recycler_view)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = timerAdapter
            }
            it.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                navigator.navigate(R.id.timerPickerFragment)
            }
            subscribeToTimerList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    private fun subscribeToTimerList() {
        timerViewModel.timers.observe(this, Observer {
            timerAdapter.submitList(it)
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onReselected() = setupActionBar()

    private fun setupActionBar() = setupActionBar(context?.getString(R.string.title_timer) ?: "Timer")

}