package com.example.module6android.task6.data.repository

import com.example.module6android.task6.data.remote.OwnServerApi
import com.example.module6android.task6.domain.model.FavoritePrize
import com.example.module6android.task6.domain.model.Prize
import com.example.module6android.task6.domain.model.PrizeLaureate
import com.example.module6android.task6.domain.repository.Task6Repository

class Task6RepositoryImpl(private val api: OwnServerApi = OwnServerApi()) : Task6Repository {

    override suspend fun login(username: String, password: String): String =
        api.login(username, password).token

    override suspend fun getPrizes(): List<Prize> =
        api.getPrizes().map { dto ->
            Prize(
                id = dto.id,
                awardYear = dto.awardYear,
                category = dto.category,
                fullName = dto.fullName,
                motivation = dto.motivation,
                detailLink = dto.detailLink,
                laureates = dto.laureates.map { l ->
                    PrizeLaureate(l.id, l.fullName, l.portion, l.motivation, l.portraitUrl)
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
