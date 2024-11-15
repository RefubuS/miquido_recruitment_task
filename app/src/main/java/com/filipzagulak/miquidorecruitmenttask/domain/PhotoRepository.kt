package com.filipzagulak.miquidorecruitmenttask.domain

import com.filipzagulak.miquidorecruitmenttask.data.model.Photo

interface PhotoRepository {
    suspend fun fetchPhotos(page: Int): Result<List<Photo>>
}
