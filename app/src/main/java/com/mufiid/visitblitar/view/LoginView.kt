package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.UserEntity

interface LoginView: LoadingView {
    fun successLogin(message: String?, user: UserEntity)
    fun failedLogin(message: String?)
}