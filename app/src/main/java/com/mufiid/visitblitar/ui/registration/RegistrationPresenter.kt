package com.mufiid.visitblitar.ui.registration

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.RegistrationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationPresenter(private val registrationView: RegistrationView) {

    fun register(
        username: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        password: String?
    ) {
        registrationView.loading()
        CompositeDisposable().add(
            ApiConfig.instance()
                .register(username, firstName, lastName, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> it.data?.let { it1 ->
                            registrationView.successRegistration(
                                it.message,
                                it1
                            )
                        }
                        else -> registrationView.failedRegistration(it.message)
                    }
                    registrationView.loading(false)
                }, {
                    registrationView.failedRegistration(it.message)
                    registrationView.loading(false)
                })
        )
    }
}