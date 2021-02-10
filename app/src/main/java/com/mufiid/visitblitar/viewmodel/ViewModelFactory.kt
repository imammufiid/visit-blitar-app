package com.mufiid.visitblitar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mufiid.visitblitar.data.TourismRepository
import com.mufiid.visitblitar.di.Injection
import com.mufiid.visitblitar.ui.reservation.ReservationViewModel

class ViewModelFactory private constructor(private val mTourismRepository: TourismRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context) : ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReservationViewModel::class.java) -> {
                ReservationViewModel(mTourismRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: ${modelClass.name}")
        }
    }
}