package com.plusmobileapps.clock.data

interface Repository {
    fun getAllAlarms(): List<Alarms>
}