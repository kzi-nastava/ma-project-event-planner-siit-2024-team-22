package com.example.eventplannerteam22.solutions

import retrofit2.http.GET
import retrofit2.http.Query

interface SolutionApi {
    @GET("/solutions")
    suspend fun getSolutions(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Solution>
}