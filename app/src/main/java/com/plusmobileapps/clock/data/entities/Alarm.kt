package com.plusmobileapps.clock.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.time.DayOfWeek

@Entity
data class Alarm(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "min")
        val min: Int,

        @ColumnInfo(name = "hour")
        val hour: Int,

        @ColumnInfo(name = "enabled")
        val enabled: Boolean = true,

        @ColumnInfo(name = "daysToRepeat")
        val daysToRepeat: List<DayOfWeek>,

        @ColumnInfo(name = "isRepeating")
        val isRepeating: Boolean = false ) {
}