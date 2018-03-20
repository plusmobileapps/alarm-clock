package com.plusmobileapps.clock

import android.app.Application
import android.content.Context
import com.plusmobileapps.clock.data.AppDatabase
import com.plusmobileapps.clock.data.Repository


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