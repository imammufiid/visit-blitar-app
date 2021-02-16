package com.mufiid.visitblitar.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.data.TourismRepository
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.data.source.remote.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TicketViewModel(private val tourismRepository: TourismRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _ticket = MutableLiveData<List<TicketEntity>>()
    val ticket : LiveData<List<TicketEntity>> = _ticket

    fun getMyTicket(userId: Int) {
        // coroutine scope view model
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val result = ApiConfig.instance().getMyTicket(userId)
//                val result = tourismRepository.getAllMyTicket(userId)
                when (result.status) {
                    200 -> _ticket.postValue(result.data)
                    else -> _message.postValue(result.message)
                }
                _loading.postValue(false)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> _message.postValue("Network Error")
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = throwable.message()
                        _message.postValue("Error $errorResponse")
                    }
                    else -> _message.postValue("Unknown error")
                }
                _loading.postValue(false)
            }

        }
    }

}