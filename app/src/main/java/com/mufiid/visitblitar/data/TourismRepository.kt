package com.mufiid.visitblitar.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mufiid.visitblitar.data.source.local.LocalDataSource
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.data.source.remote.ApiResponse
import com.mufiid.visitblitar.data.source.remote.RemoteDataSource
import com.mufiid.visitblitar.utils.AppExecutors
import com.mufiid.visitblitar.vo.Resource

class TourismRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): TourismDataSource {

    companion object {
        @Volatile
        private var instance : TourismRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): TourismRepository =
            instance ?: synchronized(this) {
                instance ?: TourismRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun getAllTourism(): LiveData<Resource<PagedList<TourismEntity>>> {
        return object : NetworkBoundResource<PagedList<TourismEntity>, List<TourismEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TourismEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTourism(), config).build()
            }

            override fun shouldFetch(data: PagedList<TourismEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TourismEntity>>> {
                return remoteDataSource.getTourism()
            }

            override fun saveCallResult(data: List<TourismEntity>) {
                val tourismList = ArrayList<TourismEntity>()
                for (response in data) {
                    val tourism = TourismEntity(
                        response.id,
                        response.categoryId,
                        response.nameTouristAttraction,
                        response.address,
                        response.capasity,
                        response.priceTicket,
                        response.totalVisitorToday,
                        response.description,
                        response.image,
                        response.latitude,
                        response.longitude
                    )
                    tourismList.add(tourism)
                }
                localDataSource.insertTourism(tourismList)
            }
        }.asLiveData()
    }
}