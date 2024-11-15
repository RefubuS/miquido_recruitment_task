package com.filipzagulak.miquidorecruitmenttask.data.model

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)
