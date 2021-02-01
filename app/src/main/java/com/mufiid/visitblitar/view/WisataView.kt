package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.TourismEntity

interface WisataView: LoadingView {
    fun getDataWisata(message: String?, data: List<TourismEntity>?)
    fun getDataWisataRecommended(message: String?, data: List<TourismEntity>?)
    fun failedGetDataWisata(message: String?)
}