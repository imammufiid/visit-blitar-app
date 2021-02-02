package com.mufiid.visitblitar.data.source.local

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
}