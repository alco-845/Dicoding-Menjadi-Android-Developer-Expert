package com.dicoding.core.di

import android.content.Context
import com.dicoding.core.data.TourismRepository
import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.local.room.TourismDatabase
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.data.source.remote.network.ApiConfig
import com.dicoding.core.domain.repository.ITourismRepository
import com.dicoding.core.domain.usecase.TourismInteractor
import com.dicoding.core.domain.usecase.TourismUseCase
import com.dicoding.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): ITourismRepository {
        val database = TourismDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()

        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context): TourismUseCase {
        val repository = provideRepository(context)
        return TourismInteractor(repository)
    }
}
