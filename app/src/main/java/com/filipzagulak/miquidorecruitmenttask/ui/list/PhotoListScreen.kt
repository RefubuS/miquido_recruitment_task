package com.filipzagulak.miquidorecruitmenttask.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.ui.theme.Typography
import com.filipzagulak.miquidorecruitmenttask.util.ShimmerListItem
import com.filipzagulak.miquidorecruitmenttask.util.ShimmerPhoto

@Composable
fun PhotoListScreen(
    viewModel: PhotoListViewModel,
    onPhotoClick: (Photo) -> Unit
) {
    val photos by viewModel.photos.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.apiError.observeAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(scrollState.value) {
        if(!isLoading && scrollState.value >= scrollState.maxValue) {
            viewModel.loadPhotos()
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = errorMessage!!,
                duration = SnackbarDuration.Indefinite,
                actionLabel = "Ok"
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 0.dp,
                    horizontal = 8.dp
                )
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            photos.forEach { photo ->
                PhotoListItem(
                    photo = photo,
                    onClick = { onPhotoClick(photo) }
                )
            }
            if(isLoading) {
                repeat(5) {
                    ShimmerListItem()
                }
            }
        }
    }
}

@Composable
fun PhotoListItem(
    photo: Photo,
    onClick: () -> Unit
) {
    var imageLoaded by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Text(
            text = "Photo with ID: ${photo.id}",
            style = Typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Center
        )

        AsyncImage(
            model = photo.download_url,
            onSuccess = {
                imageLoaded = true
            },
            contentDescription = "Photo by ${photo.author}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 0.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable(onClick = onClick)
        )
        if(!imageLoaded) {
            ShimmerPhoto()
        }
    }
}
