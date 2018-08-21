package com.plusmobileapps.clock.stopwatch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.main.OnReselectedDelegate
import com.plusmobileapps.clock.util.isSectionVisible
import com.plusmobileapps.clock.util.setupActionBar

class StopwatchFragment : Fragment(), OnReselectedDelegate {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isSectionVisible()) setupActionBar()
    }

    companion object {
        fun newInstance() = StopwatchFragment()
    }

    override fun onReselected() = setupActionBar()

    private fun setupActionBar() = setupActionBar(context?.getString(R.string.title_stopwatch) ?: "Stopwatch")
}
