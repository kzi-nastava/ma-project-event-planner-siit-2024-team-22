package com.example.eventplannerteam22.solutions.data

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import com.example.eventplannerteam22.solutions.domain.CreateSolutionDTO
import com.example.eventplannerteam22.solutions.domain.Solution
import okhttp3.OkHttpClient
import javax.inject.Inject

class SolutionRepository @Inject constructor(
    private val solutionApi: SolutionApi,
    private val okHttpClient: OkHttpClient
) {
    suspend fun getSolutions(limit: Int, offset: Int): List<Solution> {
        return solutionApi.getSolutions(limit, offset)
    }
    
    suspend fun getSolutionById(id: Int): Solution {
        return solutionApi.getSolutionById(id)
    }
    
    suspend fun addSolution(solution: CreateSolutionDTO) {
        return solutionApi.addSolution(solution)
    }
    
    suspend fun searchAndFilterSolutions(
        name: String? = null,
        description: String? = null,
        categoryId: Int? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        minDiscount: Double? = null,
        maxDiscount: Double? = null
    ): ApiResult<List<Solution>> {
        return safeApiCall(okHttpClient) { 
            val response = solutionApi.searchAndFilterSolutions(
                name = name,
                description = description,
                categoryId = categoryId,
                minPrice = minPrice,
                maxPrice = maxPrice,
                minDiscount = minDiscount,
                maxDiscount = maxDiscount
            )
            // Обрабатываем 204 No Content как пустой список
            if (response.code() == 204) {
                emptyList()
            } else {
                response.body() ?: emptyList()
            }
        }
    }
}