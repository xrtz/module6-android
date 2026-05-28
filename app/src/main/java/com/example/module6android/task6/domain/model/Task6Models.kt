package com.example.module6android.task6.domain.model

data class Prize(
    val id: String,
    val year: Int,
    val category: String,
    val laureates: List<PrizeLaureate>
)

data class PrizeLaureate(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String
)

data class FavoritePrize(
    val prizeId: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String
)
