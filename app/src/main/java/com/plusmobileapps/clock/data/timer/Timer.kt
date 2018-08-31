package com.plusmobileapps.clock.data.timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.absoluteValue

@Entity
data class Timer(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "timerText")
        val timerText: String,

        @ColumnInfo(name = "progress")
        val progress: Int = 0,

        @ColumnInfo(name = "label")
        val label: String? = null,

        @ColumnInfo(name = "startTimeMillis")
        var startTimeMillis: Long,

        @ColumnInfo(name = "hours")
        val hours: Int = 0,

        @ColumnInfo(name = "minutes")
        val minutes: Int = 0,

        @ColumnInfo(name = "seconds")
        val seconds: Int = 0,

        @ColumnInfo(name = "currentTimeLeftMillis")
        var currentTimeLeftMillis: Long) {

        fun resetTimer() {
                currentTimeLeftMillis = startTimeMillis.absoluteValue
        }

        fun getDisplayString() : String {
                return "$hours:$minutes:$seconds"
        }
}