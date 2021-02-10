package com.mufiid.visitblitar.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mufiid.visitblitar.api.ApiConfig
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.data.source.remote.response.WrappedListResponses
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RemoteDataSource {
    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getTourism(): LiveData<ApiResponse<List<TourismEntity>>> {
        val result = MutableLiveData<ApiResponse<List<TourismEntity>>>()
        CompositeDisposable().add(
            ApiConfig.instance().getListOfTouristAttraction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> result.value = ApiResponse.success(it.data)
                        404 -> result.value = ApiResponse.empty(it.message)
                        else -> result.value = ApiResponse.failed(it.message)
                    }
                }, {
                    result.value = ApiResponse.error(it.message)
                })
        )
        return result
    }
}