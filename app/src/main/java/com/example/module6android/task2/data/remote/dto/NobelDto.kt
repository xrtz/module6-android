package com.example.module6android.task2.data.remote.dto

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

@Serializable
data class PlaceDto(
    val city: LocalizedStringDto? = null,
    val country: LocalizedStringDto? = null,
    val cityNow: LocalizedStringDto? = null,
    val countryNow: LocalizedStringDto? = null
)

@Serializable
data class BirthDto(
    val date: String? = null,
    val place: PlaceDto? = null
)

@Serializable
data class WikipediaDto(
    val english: String? = null
)

@Serializable
data class LaureateDetailDto(
    val id: String? = null,
    val knownName: LocalizedStringDto? = null,
    val fullName: LocalizedStringDto? = null,
    val fileName: String? = null,
    val gender: String? = null,
    val birth: BirthDto? = null,
    val wikipedia: WikipediaDto? = null
)
