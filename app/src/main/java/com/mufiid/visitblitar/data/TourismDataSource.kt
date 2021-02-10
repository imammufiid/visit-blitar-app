package com.mufiid.visitblitar.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.data.source.remote.response.WrappedListResponses
import com.mufiid.visitblitar.data.source.remote.response.WrappedResponse
import com.mufiid.visitblitar.vo.Resource

interface TourismDataSource {
    fun getAllTourism() : LiveData<Resource<PagedList<TourismEntity>>>
//    fun recommendedTourism() : LiveData<Resource<PagedList<TourismEntity>>>
//    fun randomTourism() : LiveData<Resource<PagedList<TourismEntity>>>
//    fun getAllTicket() : LiveData<Resource<PagedList<TicketEntity>>>
//    fun getTicketById(ticketId: Int) : LiveData<Resource<TicketEntity>>
//    fun getTourismById(tourismId: Int) : LiveData<Resource<TourismEntity>>
}