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
        val timerText: String? = null,

        @ColumnInfo(name = "progress")
        val progress: Int = 0,

        @ColumnInfo(name = "label")
        val label: String? = null,

        @ColumnInfo(name = "startTimeMillis")
        var startTimeMillis: Long,

        @ColumnInfo(name = "originalStartTimeMillis")
        val originalStartTimeMillis: Long,

//        @ColumnInfo(name = "hours")
//        val hours: Int = 0,
//
//        @ColumnInfo(name = "minutes")
//        val minutes: Int = 0,
//
//        @ColumnInfo(name = "seconds")
//        val seconds: Int = 0,

        @ColumnInfo(name = "isInProgress")
        val isInProgress: Boolean = false) {

        fun getDisplayString() : String {
                val seconds = (startTimeMillis / 1000) % 60
                val minutes = (startTimeMillis / (1000 * 60)) % 60
                val hour = (startTimeMillis / (1000 * 60 * 60)) % 24
                return "${getNumberDisplay(hour)}:${getNumberDisplay(minutes)}:${getNumberDisplay(seconds)}"
        }

        private fun getNumberDisplay(number: Long) : String {
                val num = number.toInt()
                return when (num) {
                        0 -> "00"
                        in 1..9 -> "0$num"
                        else -> "$num"
                }
        }
}