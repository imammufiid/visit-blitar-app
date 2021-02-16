package com.mufiid.visitblitar.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.data.source.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel: ViewModel() {

    val message = MutableLiveData<String>()
    val user = MutableLiveData<UserEntity>()
    val loading = MutableLiveData<Boolean>()

    fun loginUser(username: String?, password: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    loading.postValue(true)
                    val result = ApiConfig.instance().loginUser(username, password)
                    when (result.status) {
                        200 -> user.postValue(result.data)
                        else -> message.postValue(result.message)
                    }
                    loading.postValue(false)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> message.postValue("Network Error")
                        is HttpException -> {
                            val code = throwable.code()
                            val errorResponse = throwable.message()
                            message.postValue("Error $errorResponse")
                        }
                        else -> message.postValue("Unknown error")
                    }
                    loading.postValue(false)
                }
            }
        }
    }
}