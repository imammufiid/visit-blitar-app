package com.mufiid.visitblitar.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity

interface TourismDao {

    @Query("SELECT * FROM tourismentities")
    fun getTourism(): DataSource.Factory<Int, TourismEntity>

    @Query("SELECT * FROM tourismentities WHERE tourism_id = :tourismId")
    fun getTourismById(tourismId: Int?): LiveData<TourismEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTourism(tourism: List<TourismEntity>)
}