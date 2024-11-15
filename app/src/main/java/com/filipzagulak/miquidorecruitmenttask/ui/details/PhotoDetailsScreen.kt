package com.filipzagulak.miquidorecruitmenttask.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.filipzagulak.miquidorecruitmenttask.data.model.Photo

@Composable
fun PhotoDetailsScreen(
    photo: Photo
) {
    val context = LocalContext.current

    Column(Modifier.padding(16.dp)) {
        AsyncImage(
            model = photo.download_url,
            contentDescription = "Photo by ${photo.author}",
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "ID: ${photo.id}")
        Text(text = "Author: ${photo.author}")
        Text(text = "Width: ${photo.width}")
        Text(text = "Height: ${photo.height}")
        Row {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(photo.download_url)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("Download")
            }
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(photo.url)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("View on website")
            }
        }
    }
}
