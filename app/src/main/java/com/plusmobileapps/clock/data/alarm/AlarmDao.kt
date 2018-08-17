package com.plusmobileapps.clock.data.alarm

import androidx.lifecycle.LiveData
import androidx.room.*
import com.plusmobileapps.clock.data.alarm.Alarm

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm")
    fun getAll(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE id IN (:id)")
    fun getById(id: Int): LiveData<Alarm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)
}