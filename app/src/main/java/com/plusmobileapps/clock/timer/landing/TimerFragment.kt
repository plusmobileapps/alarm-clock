package com.plusmobileapps.clock.timer.landing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.databinding.FragmentTimerBinding
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.timer.picker.TimerPickerViewModel
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import javax.inject.Inject


class TimerFragment : Fragment(), OnReselectedDelegate {

    companion object {
        fun newInstance() = TimerFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var timerViewModel: TimerViewModel
    private lateinit var timerPickerViewModel: TimerPickerViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navigator: NavController

    private lateinit var timerAdapter: TimerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyApplication.appComponent.inject(this)
        val binding = DataBindingUtil.inflate<FragmentTimerBinding>(inflater, R.layout.fragment_timer, container, false)
        timerViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerViewModel::class.java)
        timerPickerViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerPickerViewModel::class.java)

        binding.apply {
            viewmodel = timerViewModel
            setLifecycleOwner(this@TimerFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        timerAdapter = TimerAdapter(timerViewModel)
        view?.let {
            navigator = Navigation.findNavController(it)
            recyclerView = it.findViewById<RecyclerView>(R.id.recycler_view).apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = timerAdapter
            }
            subscribe()
        }
    }

    private fun subscribe() {
        with(timerViewModel) {
            timers.observe(this@TimerFragment, Observer { timers ->
                timerAdapter.submitList(timers)
            })
            showTimerPicker.observe(this@TimerFragment, Observer {
                navigator.navigate(R.id.action_timerFragment_to_timerPickerFragment)
            })
        }
        timerPickerViewModel.startTimer.observe(this@TimerFragment, Observer { timer ->
            timerViewModel.toggleTimer(timer)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    override fun onReselected() = setupActionBar()

    private fun setupActionBar() = setupActionBar(requireContext().getString(R.string.title_timer))

}