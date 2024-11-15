package com.filipzagulak.miquidorecruitmenttask.data.repository

import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.data.remote.ApiService
import com.filipzagulak.miquidorecruitmenttask.domain.PhotoRepository
import okio.IOException
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PhotoRepository {

    override suspend fun fetchPhotos(page: Int): Result<List<Photo>> {
        return try {
            val photos = apiService.getPhotos(page)
            Result.success(photos)
        } catch(e: IOException) {
            Result.failure(Throwable(e.message))
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }
}
