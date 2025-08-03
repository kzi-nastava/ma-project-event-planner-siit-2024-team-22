package com.example.eventplannerteam22.priceList.data

import com.example.eventplannerteam22.priceList.domen.PriceListItem
import com.example.eventplannerteam22.session.SessionRepository
import java.math.BigDecimal
import javax.inject.Inject

class PriceListRepository @Inject constructor(
    private val apiService: PriceListApiService,
    private val sessionRepository: SessionRepository
) {
    suspend fun getPriceList(): List<PriceListItem> {
        val accessToken = sessionRepository.getAccessToken()
        if (accessToken.isEmpty()) {
            throw Exception()
        }

        return apiService.getPriceList(
            "Bearer $accessToken"
        ).map { dto ->
            PriceListItem(
                name = dto.name,
                price = dto.price,
                discount = dto.discount ?: BigDecimal.ZERO,
                type = dto.type
            )
        }
    }

    suspend fun updatePriceListItem(item: PriceListItem) {
        // Реализация обновления на сервере
        // Пока просто заглушка
    }
}