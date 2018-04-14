package com.plusmobileapps.clock.di

import android.app.Application
import android.content.Context
import com.plusmobileapps.clock.data.AppDatabase


class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()

    }

    companion object {
        lateinit var instance: MyApplication
            private set

        fun getContext(): Context {
            return instance.applicationContext
        }

        fun getDatabase(): AppDatabase {
            return AppDatabase.getInstance(instance.applicationContext)!!
        }

    }

}