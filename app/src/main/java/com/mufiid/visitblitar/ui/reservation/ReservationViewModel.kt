package com.mufiid.visitblitar.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mufiid.visitblitar.data.TourismRepository
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.vo.Resource

class ReservationViewModel(private val tourismRepository: TourismRepository): ViewModel() {
    fun getAllTourism(): LiveData<Resource<PagedList<TourismEntity>>> = tourismRepository.getAllTourism()
}