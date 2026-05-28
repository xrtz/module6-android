package com.example.module6android.task2.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalizedStringDto(
    val en: String? = null,
    val se: String? = null,
    val no: String? = null
)

@Serializable
data class LaureateSummaryDto(
    val id: String? = null,
    val fullName: LocalizedStringDto? = null,
    val portion: String? = null,
    val motivation: LocalizedStringDto? = null
)

@Serializable
data class NobelPrizeDto(
    val awardYear: String? = null,
    val category: LocalizedStringDto? = null,
    val categoryFullName: LocalizedStringDto? = null,
    val dateAwarded: String? = null,
    val laureates: List<LaureateSummaryDto>? = null
)

@Serializable
data class NobelPrizeResponse(
    val nobelPrizes: List<NobelPrizeDto>? = null
)
