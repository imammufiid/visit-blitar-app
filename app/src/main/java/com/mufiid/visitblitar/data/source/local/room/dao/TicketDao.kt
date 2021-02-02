package com.mufiid.visitblitar.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Query
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity

interface TicketDao {

    @Query("SELECT * FROM ticketentities")
    fun getTicket(): DataSource.Factory<Int, TicketEntity>

    @Query("SELECT * FROM ticketentities WHERE ticket_id = :ticketId")
    fun getTicketById(ticketId: Int?) : LiveData<TicketEntity>
}