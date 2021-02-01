package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.TicketEntity

interface DetailTicketView: LoadingView {
    fun message(message: String?)
    fun getDetailTicket(message: String?, data: TicketEntity?)
}