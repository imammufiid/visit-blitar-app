package com.mufiid.visitblitar.data.source.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.data.source.local.room.dao.TourismDao

@Database(entities = [TourismEntity::class], version = 1, exportSchema = false)
abstract class TourismDatabase: RoomDatabase() {
    abstract fun tourismDao(): TourismDao

    companion object {
        @Volatile
        private var INSTANCE: TourismDatabase? = null

        fun getInstance(context: Context): TourismDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TourismDatabase::class.java,
                    "Tourism.db"
                ).build()
            }
    }
}