package com.filipzagulak.miquidorecruitmenttask

import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.data.remote.ApiService
import com.filipzagulak.miquidorecruitmenttask.data.repository.PhotoRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: PhotoRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PhotoRepositoryImpl(apiService)
    }

    @Test
    fun `fetchPhotos returns success when API call is successful`() = runBlocking {
        val mockPhotos = listOf(
            Photo("1","XYZ", 1000, 1000, "url_1", "download_url_1"),
            Photo("2","ABC", 1920, 1080, "url_2", "download_url_2")
        )
        coEvery { apiService.getPhotos(1) } returns mockPhotos

        val result = repository.fetchPhotos(1)

        assertTrue(result.isSuccess)
        assertEquals(mockPhotos, result.getOrNull())
    }

    @Test
    fun `fetchPhotos returns failure when API throws IOException`() = runBlocking {
        val exception = IOException("Network error")
        coEvery { apiService.getPhotos(1) } throws exception

        val result = repository.fetchPhotos(1)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchPhotos returns failure when API throws unexpected exception`() = runBlocking {
        val exception = RuntimeException("Unexpected error")
        coEvery { apiService.getPhotos(1) } throws exception

        val result = repository.fetchPhotos(1)

        assertTrue(result.isFailure)
        assertEquals("Unexpected error", result.exceptionOrNull()?.message)
    }
}
