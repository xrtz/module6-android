package com.example.module6android.task2.domain.model

data class NobelPrize(
    val awardYear: String,
    val category: String,
    val categoryFullName: String,
    val laureates: List<Laureate>
)

data class Laureate(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String
)
