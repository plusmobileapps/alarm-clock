package com.plusmobileapps.clock.data.timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.absoluteValue

@Entity
data class Timer(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "startTimeMillis")
        var startTimeMillis: Long,

        @ColumnInfo(name = "currentTimeLeftMillis")
        var currentTimeLeftMillis: Long) {

        fun resetTimer() {
                currentTimeLeftMillis = startTimeMillis.absoluteValue
        }
}