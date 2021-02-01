package com.mufiid.visitblitar.ui.detailticket

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.DetailTicketView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailTicketPresenter(private val detailTicketView: DetailTicketView) {

    fun isComing(userId: Int, codeReservation: String) {
        detailTicketView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().isComing(userId, codeReservation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        201 -> detailTicketView.message(it.message)
                        else -> detailTicketView.message(it.message)
                    }
                    detailTicketView.loading(false)
                }, {
                    detailTicketView.message(it.message)
                    detailTicketView.loading(false)
                })
        )
    }

    fun getTicketById(ticketId: Int?) {
        detailTicketView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().getTicketById(ticketId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> detailTicketView.getDetailTicket(it.message, it.data)
                        else -> detailTicketView.message(it.message)
                    }
                    detailTicketView.loading(false)
                }, {
                    detailTicketView.message(it.message)
                    detailTicketView.loading(false)
                })
        )
    }
}