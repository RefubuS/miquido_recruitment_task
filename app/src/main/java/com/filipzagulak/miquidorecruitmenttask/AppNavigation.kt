package com.filipzagulak.miquidorecruitmenttask

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.filipzagulak.miquidorecruitmenttask.data.model.Photo
import com.filipzagulak.miquidorecruitmenttask.ui.details.PhotoDetailsScreen
import com.filipzagulak.miquidorecruitmenttask.ui.list.PhotoListScreen
import com.filipzagulak.miquidorecruitmenttask.ui.list.PhotoListViewModel
import com.google.gson.Gson

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "photo_list") {
        composable("photo_list") {
            val viewModel = hiltViewModel<PhotoListViewModel>()

            PhotoListScreen(
                viewModel = viewModel,
                onPhotoClick = { photo ->
                    navController.navigate("photo_details/${Uri.encode(Gson().toJson(photo))}")
                }
            )
        }
        composable("photo_details/{photoJson}") { backStackEntry ->
            val photoJson = backStackEntry.arguments?.getString("photoJson")
            val photo = Gson().fromJson(photoJson, Photo::class.java)

            PhotoDetailsScreen(photo = photo)
        }
    }
}
