package com.mufiid.visitblitar.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity

@Dao
interface TicketDao {

    @Query("SELECT * FROM ticketentities WHERE user_id = :userId")
    fun getAllTicketByUser(userId: Int?): DataSource.Factory<Int, TicketEntity>

    @Query("SELECT * FROM ticketentities WHERE ticket_id = :ticketId")
    fun getTicketById(ticketId: Int?) : LiveData<TicketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: List<TicketEntity>)
}