package com.mufiid.visitblitar.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.data.source.local.room.dao.TicketDao
import com.mufiid.visitblitar.data.source.local.room.dao.TourismDao

class LocalDataSource private constructor(
    private val mTicketDao: TicketDao,
    private val mTourismDao: TourismDao
){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(ticketDao: TicketDao, tourismDao: TourismDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(ticketDao, tourismDao)
    }

    // ticket entity
    fun getAllTicketByUser(userId: Int?): DataSource.Factory<Int, TicketEntity> = mTicketDao.getAllTicketByUser(userId)
    fun getTicketById(ticketId: Int?) : LiveData<TicketEntity> = mTicketDao.getTicketById(ticketId)
}