package com.mufiid.visitblitar.di

import android.content.Context
import com.mufiid.visitblitar.data.TourismRepository
import com.mufiid.visitblitar.data.source.local.LocalDataSource
import com.mufiid.visitblitar.data.source.local.room.database.TicketDatabase
import com.mufiid.visitblitar.data.source.local.room.database.TourismDatabase
import com.mufiid.visitblitar.data.source.remote.RemoteDataSource
import com.mufiid.visitblitar.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) : TourismRepository {
        val tourismDatabase = TourismDatabase.getInstance(context)
        val ticketDatabase = TicketDatabase.getInstance(context)

        val localDataSource = LocalDataSource.getInstance(ticketDatabase.ticketDao(), tourismDatabase.tourismDao())
        val remoteDataSource = RemoteDataSource.getInstance()
        val appExecutors = AppExecutors()
        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}