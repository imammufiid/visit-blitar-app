package com.mufiid.visitblitar.ui.login

import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.view.LoginView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(private val loginView: LoginView) {
    fun login(username: String?, password: String?) {
        loginView.loading()
        CompositeDisposable().add(
            ApiConfig.instance().login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> it.data?.let { it1 -> loginView.successLogin(it.message, it1) }
                        400 -> loginView.failedLogin(it.message)
                        else -> loginView.failedLogin(it.message)
                    }
                    loginView.loading(false)
                }, {
                    loginView.failedLogin(it.message)
                    loginView.loading(false)
                })
        )
    }
}