package com.filipzagulak.miquidorecruitmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.filipzagulak.miquidorecruitmenttask.ui.theme.MiquidoRecruitmentTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MiquidoRecruitmentTaskTheme {
                AppNavigation(navController = navController)
            }
        }
    }
}
