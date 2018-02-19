package com.plusmobileapps.clock.data

import com.plusmobileapps.clock.data.entities.Alarm

interface Repository {
    fun getAllAlarms(): List<Alarm>
}