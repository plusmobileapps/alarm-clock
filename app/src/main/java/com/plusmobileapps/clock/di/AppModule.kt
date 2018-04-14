package com.plusmobileapps.clock.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class AppModule (private val application: MyApplication) {

    @Provides
    fun providesMyApplication(): MyApplication = application

    @Provides
    fun providesApplication(): Application = application

}