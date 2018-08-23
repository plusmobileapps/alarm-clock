package com.plusmobileapps.clock.util

import android.view.ViewGroup

fun <T> T?.or(default: T): T = this ?: default
fun <T> T?.or(compute: () -> T): T = this ?: compute()

