package com.mufiid.visitblitar.ui.scanqr

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.ScanQrView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ScanQrPresenter(private val scanQrView: ScanQrView) {

    fun scanQr() {
        scanQrView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().scanQr()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                               200 -> scanQrView.result(it.status, it.message)
                        else -> scanQrView.result(it.status, it.message)
                    }
                    scanQrView.loading(false)
                }, {
                    scanQrView.result(null, it.message)
                    scanQrView.loading(false)
                })
        )
    }
}