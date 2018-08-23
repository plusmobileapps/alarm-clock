package com.plusmobileapps.clock.util

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.isSectionVisible(): Boolean = (((view?.parent as? ViewGroup)?.parent as? ViewGroup)?.visibility == android.view.View.VISIBLE)

fun Fragment.setupActionBar(title: String, displayHome: Boolean = false) {
    (activity as? AppCompatActivity)?.supportActionBar?.apply {
        this.title = title
        setDisplayShowHomeEnabled(displayHome)
        setDisplayHomeAsUpEnabled(displayHome)
    }
    setHasOptionsMenu(displayHome)
}