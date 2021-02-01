package com.mufiid.visitblitar.ui.ticket

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.TicketView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TicketPresenter(private val ticketView: TicketView) {
    fun getAllMyTicket(userId: Int) {
        ticketView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().getAllMyTicket(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> it.data?.let { it1 ->
                            ticketView.getDataMyTicket(it.message,
                                it1
                            )
                        }
                        else -> ticketView.failedGetDataMyTicket(it.message)
                    }
                    ticketView.loading(false)
                }, {
                    ticketView.failedGetDataMyTicket(it.message)
                    ticketView.loading(false)
                })
        )
    }
}