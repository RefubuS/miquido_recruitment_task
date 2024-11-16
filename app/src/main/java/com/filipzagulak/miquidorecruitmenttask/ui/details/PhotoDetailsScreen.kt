package com.filipzagulak.miquidorecruitmenttask.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.ui.theme.Typography
import com.filipzagulak.miquidorecruitmenttask.util.ShimmerPhoto

@Composable
fun PhotoDetailsScreen(
    photo: Photo
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var imageLoaded by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        AsyncImage(
            model = photo.download_url,
            contentDescription = "Photo by ${photo.author}",
            onSuccess = {
                imageLoaded = true
            },
            modifier = Modifier
                .padding(
                    horizontal = 0.dp,
                    vertical = 4.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
        )
        if(!imageLoaded) {
            ShimmerPhoto()
        }

        DataLabel("ID", photo.id)
        DataLabel("Author", photo.author)
        DataLabel("Width", photo.width.toString())
        DataLabel("Height", photo.height.toString())
        DataLabel("Download URL", photo.download_url)

        Button(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(photo.url)
                }
                context.startActivity(intent)
            }
        ) {
            Text(
                text = "View more details on website",
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.labelLarge
            )
        }
    }
}

@Composable
fun DataLabel(
    name: String,
    data: String
) {
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(
                    horizontal = 0.dp,
                    vertical = 4.dp
                )
                .clip(RoundedCornerShape(4.dp, 0.dp, 0.dp, 4.dp))
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 0.dp,
                    vertical = 4.dp
                )
                .clip(RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickable {
                    clipboardManager.setText(AnnotatedString(data))
                },
        ) {
            Text(
                text = data,
                color = MaterialTheme.colorScheme.primary,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }

}
