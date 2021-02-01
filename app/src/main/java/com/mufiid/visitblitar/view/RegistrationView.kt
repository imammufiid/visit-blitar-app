package com.mufiid.visitblitar.view

import com.mufiid.visitblitar.data.source.local.entity.UserEntity

interface RegistrationView: LoadingView {
    fun successRegistration(message: String?, user: UserEntity)
    fun failedRegistration(message: String?)
}