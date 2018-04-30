package com.plusmobileapps.clock.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.time.DayOfWeek

@Entity
data class Alarm(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "min")
        var min: Int = -1,

        @ColumnInfo(name = "hour")
        var hour: Int = -1,

        @ColumnInfo(name = "enabled")
        var enabled: Boolean = true,

//        @ColumnInfo(name = "daysToRepeat")
//        var daysToRepeat: List<DayOfWeek>,

        @ColumnInfo(name = "isRepeating")
        var isRepeating: Boolean = false ) {

        fun printTime() = "$hour:$min"

}