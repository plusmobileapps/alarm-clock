package com.plusmobileapps.clock.data.timer

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TimerDao {

    @Query("SELECT * FROM timer")
    fun getAll(): LiveData<List<Timer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timer: Timer)

    @Delete
    fun delete(timer: Timer)
}