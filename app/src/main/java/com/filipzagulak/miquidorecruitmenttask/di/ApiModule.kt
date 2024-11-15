package com.filipzagulak.miquidorecruitmenttask.di

import com.filipzagulak.miquidorecruitmenttask.data.remote.ApiService
import com.filipzagulak.miquidorecruitmenttask.data.repository.PhotoRepositoryImpl
import com.filipzagulak.miquidorecruitmenttask.domain.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://picsum.photos")
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: ApiService): PhotoRepository {
        return PhotoRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }
}
