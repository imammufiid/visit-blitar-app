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

    fun getTourismRandom(random: Int? = null): LiveData<WrappedListResponses<TourismEntity>> {
        val result = MutableLiveData<WrappedListResponses<TourismEntity>>()

        CompositeDisposable().add(
            ApiConfig.instance().getListOfTouristAttraction(random = random)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result.value = WrappedListResponses(it.message, it.status, it.data)

                }, {
                    result.value = WrappedListResponses(it.message, null, null)
                })
        )
        return result
    }
}