package com.example.eventplannerteam22.solutionCategory.data;

import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import com.example.eventplannerteam22.solutions.domain.Solution
import javax.inject.Inject;

class SolutionCategoryRepository @Inject constructor(
        private val solutionCategoryApi: SolutionCategoryApi
) {
        suspend fun getSolutionCategories(limit: Int, offset: Int): List<SolutionCategory> {
                return solutionCategoryApi.getSolutionCategories(limit, offset)
        }
        suspend fun getSolutionCategoryById(id: Int): SolutionCategory {
                return solutionCategoryApi.getSolutionCategoryById(id)
        }
        suspend fun addSolutionCategory(solutionCategory: SolutionCategory) {
                return solutionCategoryApi.addSolutionCategory(solutionCategory)
        }

        suspend fun updateSolutionCategory(solutionCategory: SolutionCategory, id: Int) {
                return solutionCategoryApi.updateSolutionCategory(solutionCategory, id)
        }

        suspend fun deleteSolutionCategory(id: Int) {
                return solutionCategoryApi.deleteSolutionCategory(id)
        }
}
