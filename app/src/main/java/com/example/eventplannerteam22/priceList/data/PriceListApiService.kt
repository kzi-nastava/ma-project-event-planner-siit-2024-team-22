package com.example.eventplannerteam22.priceList.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import java.math.BigDecimal

interface PriceListApiService {
    @GET("priceList")
    suspend fun getPriceList(
        @Header("Authorization") token: String
    ): List<PriceListDto>

    @PUT("priceList/update")
    suspend fun updatePriceListItem(@Body item: PriceListDto)
}

// Соответствует бэкенд DTO
data class PriceListDto(
    val name: String,
    val price: BigDecimal,
    val discount: BigDecimal?,
    val type: String
)