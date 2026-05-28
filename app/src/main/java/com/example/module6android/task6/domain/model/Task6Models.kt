package com.example.module6android.task6.domain.model

data class Prize(
    val id: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String,
    val laureates: List<PrizeLaureate>
)

data class PrizeLaureate(
    val id: Int,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String
)

data class FavoritePrize(
    val prizeId: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String
)
