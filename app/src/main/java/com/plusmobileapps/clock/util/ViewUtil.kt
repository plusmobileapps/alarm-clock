package com.plusmobileapps.clock.util

import android.view.View

fun View.showOrGone(show: Boolean) {
    visibility = if(show) {
        View.VISIBLE
    } else{
        View.GONE
    }
}

fun View.showOrInvisible(show: Boolean) {
    visibility = if(show) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}