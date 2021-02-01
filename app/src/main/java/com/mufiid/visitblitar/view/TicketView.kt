package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.TicketEntity

interface TicketView: LoadingView {
    fun getDataMyTicket(message: String?, data: List<TicketEntity>?)
    fun failedGetDataMyTicket(message: String?)
}