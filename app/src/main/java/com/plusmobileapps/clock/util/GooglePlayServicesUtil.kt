package com.plusmobileapps.clock.util

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesUtil

fun requiresGooglePlayServices(activity: AppCompatActivity, code: () -> Unit) {

    val googlePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)
    when(googlePlayServicesAvailable){
        ConnectionResult.SUCCESS -> code()
        else -> {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity)
        }
    }

}