package com.mufiid.visitblitar.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity

@Dao
interface TourismDao {

    @Query("SELECT * FROM tourismentities")
    fun getTourism(): DataSource.Factory<Int, TourismEntity>

    @Query("SELECT * FROM tourismentities WHERE tourism_id = :tourismId")
    fun getTourismById(tourismId: Int?): LiveData<TourismEntity>

//    @Query("SELECT * FROM tourismentities ORDER BY RAND() LIMIT 6")
//    fun getRandomTourism(): DataSource.Factory<Int, TourismEntity>
//
//    @Query("SELECT * FROM tourismentities ORDER BY RAND() LIMIT 10")
//    fun getRecommendedTourism(): DataSource.Factory<Int, TourismEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTourism(tourism: List<TourismEntity>)
}