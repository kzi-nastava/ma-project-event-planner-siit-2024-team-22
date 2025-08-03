package com.example.eventplannerteam22.solutions.data

import com.example.eventplannerteam22.solutions.domain.CreateSolutionDTO
import com.example.eventplannerteam22.solutions.domain.Solution
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SolutionApi {
    @GET("/solutions")
    suspend fun getSolutions(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Solution>
    @GET("/solutions/{id}")
    suspend fun getSolutionById(@Path("id") id: Int): Solution
    @POST("/solutions")
    suspend fun addSolution(@Body solution: CreateSolutionDTO)
}