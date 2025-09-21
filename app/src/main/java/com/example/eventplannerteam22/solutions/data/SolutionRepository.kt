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
        category: String? = null,
        price: Double? = null,
        discount: Double? = null
    ): ApiResult<List<Solution>> {
        return safeApiCall(okHttpClient) { 
            solutionApi.searchAndFilterSolutions(
                name = name,
                description = description,
                category = category,
                price = price,
                discount = discount
            )
        }
    }
}