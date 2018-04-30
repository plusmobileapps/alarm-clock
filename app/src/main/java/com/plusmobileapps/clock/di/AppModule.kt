package com.plusmobileapps.clock.di

import android.app.Application
import com.plusmobileapps.clock.MyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule (private val application: MyApplication) {

    @Provides
    fun providesMyApplication(): MyApplication = application

    @Provides
    fun providesApplication(): Application = application

}