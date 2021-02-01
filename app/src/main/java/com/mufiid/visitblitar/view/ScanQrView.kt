package com.mufiid.visitblitar.view

interface ScanQrView: LoadingView {
    fun result(status: Int? = 0, message: String?)
}