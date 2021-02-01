package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.TourismEntity

interface ReservationView: LoadingView {
    fun getDataReservation(message: String?, data: List<TourismEntity>)
    fun failedGetDataReservation(message: String?)
}