package com.example.eventplannerteam22.solutionCategory.data

import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory

import retrofit2.http.*

interface SolutionCategoryApi {
    @GET("/solution_category")
    suspend fun getSolutionCategories(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<SolutionCategory>
    @GET("/solution_category/{id}")
    suspend fun getSolutionCategoryById(@Path("id") id: Int): SolutionCategory
    @POST("/solution_category")
    suspend fun addSolutionCategory(@Body solution: SolutionCategory)
    @PUT("/solution_category")
    suspend fun updateSolutionCategory(@Body newSolutionCategory: SolutionCategory)
    @DELETE("/solution_category")
    suspend fun deleteSolutionCategory(@Path("id") id: Int)
}