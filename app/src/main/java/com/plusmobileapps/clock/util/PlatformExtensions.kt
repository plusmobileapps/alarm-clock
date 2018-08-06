package com.plusmobileapps.clock.util

import android.os.Build

inline fun supportsPlatform(platformVersion: Int, code: () -> Unit) {
    if (supportsPlatform(platformVersion)) { code() }
}

fun supportsPlatform(platformVersion: Int) : Boolean {
    return Build.VERSION.SDK_INT >= platformVersion
}