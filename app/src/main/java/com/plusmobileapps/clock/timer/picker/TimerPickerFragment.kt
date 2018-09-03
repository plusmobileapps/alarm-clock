package com.plusmobileapps.clock.timer.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.databinding.TimerPickerDialogBinding
import com.plusmobileapps.clock.di.ViewModelFactory
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar
import javax.inject.Inject

class TimerPickerFragment : Fragment(), OnReselectedDelegate {

    companion object {
        fun newInstance() = TimerPickerFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var timerViewModel: TimerPickerViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        MyApplication.appComponent.inject(this)
        timerViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(TimerPickerViewModel::class.java)
        val binding = DataBindingUtil.inflate<TimerPickerDialogBinding>(inflater, R.layout.timer_picker_dialog, container, false)
        binding.apply {
            viewmodel = timerViewModel
            setLifecycleOwner(this@TimerPickerFragment)
        }
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let {
            navController = Navigation.findNavController(it)
            subscribe()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    private fun subscribe() {
        with(timerViewModel) {
            closeTimerPicker.observe(this@TimerPickerFragment, Observer {
                navController.navigateUp()
            })

            snackbarError.observe(this@TimerPickerFragment, Observer {
                Toast.makeText(context, "Need a number!", Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() = setupActionBar("Timer Picker", true)

    override fun onReselected() = setupActionBar()
}
