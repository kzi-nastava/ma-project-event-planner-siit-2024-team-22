package com.example.eventplannerteam22.solutions

import com.example.eventplannerteam22.products.Product
import javax.inject.Inject

class SolutionRepository @Inject constructor(
    private val solutionApi: SolutionApi
) {
    suspend fun getSolutions(limit: Int, offset: Int): List<Solution> {
        return solutionApi.getSolutions(limit, offset)
    }
    suspend fun getSolutionById(id: Int): Solution {
        return solutionApi.getSolutionById(id)
    }
}