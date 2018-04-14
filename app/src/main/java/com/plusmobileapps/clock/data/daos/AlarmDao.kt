package com.plusmobileapps.clock.data.daos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import com.plusmobileapps.clock.data.entities.Alarm

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