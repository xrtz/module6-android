package com.example.module6android.task2.data.repository

import com.example.module6android.task2.data.remote.NobelApi
import com.example.module6android.task2.domain.model.Laureate
import com.example.module6android.task2.domain.model.LaureateDetail
import com.example.module6android.task2.domain.model.NobelPrize
import com.example.module6android.task2.domain.repository.NobelRepository

class NobelRepositoryImpl(private val api: NobelApi) : NobelRepository {

    override suspend fun getLaureate(id: String): LaureateDetail {
        val dto = api.getLaureate(id)
        val place = dto.birth?.place
        return LaureateDetail(
            id = dto.id ?: id,
            fullName = dto.fullName?.en ?: dto.knownName?.en ?: "",
            birthDate = dto.birth?.date ?: "",
            birthCity = place?.city?.en ?: place?.cityNow?.en ?: "",
            birthCountry = place?.country?.en ?: place?.countryNow?.en ?: "",
            portraitUrl = dto.fileName
                ?.takeIf { it.isNotEmpty() }
                ?.let { "https://www.nobelprize.org/images/${it}-medium.jpg" }
                ?: "",
            wikipediaUrl = dto.wikipedia?.english ?: ""
        )
    }

    override suspend fun getPrizes(year: String?, category: String?): List<NobelPrize> {
        val response = api.getPrizes(limit = 50, year = year, category = category)
        return response.nobelPrizes?.map { dto ->
            NobelPrize(
                awardYear = dto.awardYear ?: "",
                category = dto.category?.en ?: "",
                categoryFullName = dto.categoryFullName?.en ?: "",
                laureates = dto.laureates?.map { l ->
                    Laureate(
                        id = l.id ?: "",
                        fullName = l.fullName?.en ?: "Организация",
                        portion = l.portion ?: "",
                        motivation = l.motivation?.en ?: ""
                    )
                } ?: emptyList()
            )
        } ?: emptyList()
    }
}
