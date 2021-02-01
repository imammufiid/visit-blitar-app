package com.mufiid.visitblitar.ui.addreservation

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.AddReservationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddReservationPresenter(private val addReservationView: AddReservationView) {
    fun addReservation(userId: Int, nameVisitor: String?, tourismId: Int?, email: String?, date: String?, totalVisitors: Int?, nik: String?, noTelp: String?) {
        addReservationView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().addReservation(userId, nameVisitor, tourismId, email, date, totalVisitors, nik, noTelp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        201 -> addReservationView.messageAddReservation(it.message)
                        else -> addReservationView.messageAddReservation(it.message)
                    }
                    addReservationView.loading(false)
                }, {
                    addReservationView.messageAddReservation(it.message)
                    addReservationView.loading(false)
                })
        )
    }
}