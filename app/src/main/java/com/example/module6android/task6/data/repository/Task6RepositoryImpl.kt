package com.example.module6android.task6.data.repository

import com.example.module6android.task6.data.remote.OwnServerApi
import com.example.module6android.task6.domain.model.FavoritePrize
import com.example.module6android.task6.domain.model.Prize
import com.example.module6android.task6.domain.model.PrizeLaureate
import com.example.module6android.task6.domain.repository.Task6Repository

class Task6RepositoryImpl(private val api: OwnServerApi = OwnServerApi()) : Task6Repository {

    override suspend fun login(username: String, password: String): String =
        api.login(username, password).token

    override suspend fun getPrizes(token: String): List<Prize> =
        api.getPrizes(token).map { dto ->
            Prize(
                id = dto.id,
                year = dto.year,
                category = dto.category,
                laureates = dto.laureates.map { l ->
                    PrizeLaureate(l.id, l.fullName, l.portion, l.motivation)
                }
            )
        }

    override suspend fun getPrize(token: String, year: Int, category: String): Prize {
        val dto = api.getPrize(token, year, category)
        return Prize(
            id = dto.id,
            year = dto.year,
            category = dto.category,
            laureates = dto.laureates.map { l ->
                PrizeLaureate(l.id, l.fullName, l.portion, l.motivation)
            }
        )
    }

    override suspend fun getFavorites(token: String): List<FavoritePrize> =
        api.getFavorites(token).map { dto ->
            FavoritePrize(dto.prizeId, dto.awardYear, dto.category, dto.fullName, dto.motivation)
        }

    override suspend fun addFavorite(token: String, prizeId: Int) =
        api.addFavorite(token, prizeId)

    override suspend fun removeFavorite(token: String, prizeId: Int) =
        api.removeFavorite(token, prizeId)
}
