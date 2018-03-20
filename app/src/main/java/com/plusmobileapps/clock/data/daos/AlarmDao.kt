package com.plusmobileapps.clock.data.daos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.plusmobileapps.clock.data.entities.Alarm


interface AlarmDao {

    @Query("SELECT * FROM alarm")
    fun getAll(): MutableLiveData<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE id IN (:id)")
    fun getById(id: Int): MutableLiveData<Alarm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)
}