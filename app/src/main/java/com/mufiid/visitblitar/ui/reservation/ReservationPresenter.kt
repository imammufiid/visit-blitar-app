package com.mufiid.visitblitar.ui.reservation

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.ReservationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ReservationPresenter(private val reservationView: ReservationView) {
    fun getReservation(query: String? = null) {
        reservationView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().getListOfTouristAttraction(query = query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> it.data?.let { it1 ->
                            reservationView.getDataReservation(it.message,
                                it1
                            )
                        }
                        else -> reservationView.failedGetDataReservation(it.message)
                    }
                    reservationView.loading(false)
                }, {
                    reservationView.failedGetDataReservation(it.message)
                    reservationView.loading(false)
                })
        )
    }
}