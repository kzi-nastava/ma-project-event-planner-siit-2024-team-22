package com.example.eventplannerteam22.solutions

import javax.inject.Inject

class SolutionRepository @Inject constructor(
    private val solutionApi: SolutionApi
) {
    suspend fun getSolutions(limit: Int, offset: Int): List<Solution> {
        return solutionApi.getSolutions(limit, offset)
    }
}