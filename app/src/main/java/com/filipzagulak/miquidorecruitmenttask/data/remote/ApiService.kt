package com.filipzagulak.miquidorecruitmenttask.data.remote

import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): List<Photo>

}
