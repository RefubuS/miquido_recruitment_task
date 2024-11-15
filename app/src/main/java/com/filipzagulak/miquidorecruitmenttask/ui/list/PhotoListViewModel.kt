package com.filipzagulak.miquidorecruitmenttask.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.data.repository.PhotoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val photoRepository: PhotoRepositoryImpl
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apiError = MutableLiveData<String?>()
    val apiError: LiveData<String?> = _apiError

    private var currentPage = 1

    fun loadPhotos() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = photoRepository.fetchPhotos(currentPage)
            if (result.isSuccess) {
                _photos.value = _photos.value.orEmpty() + result.getOrDefault(emptyList())
                currentPage++
            } else {
                val exception = result.exceptionOrNull()
                _apiError.value = exception?.message
            }
            _isLoading.value = false
        }
    }
}
