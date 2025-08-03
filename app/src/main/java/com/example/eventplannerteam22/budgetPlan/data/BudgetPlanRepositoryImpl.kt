package com.example.eventplannerteam22.budgetPlan.data

import com.example.eventplannerteam22.budgetPlan.presentation.model.Category
import javax.inject.Inject

class BudgetPlanRepositoryImpl @Inject constructor(
    private val apiService: BudgetPlanApiService
) : BudgetPlanRepository {
    override suspend fun createBudgetPlan(eventId: Int): BudgetPlanResponse {
        return apiService.createBudgetPlan(eventId)
    }

    override suspend fun getBudgetPlan(eventId: Int): BudgetPlanResponse {
        return apiService.getBudgetPlan(eventId)
    }

    override suspend fun getCategories(): List<Category> {
        return apiService.getCategories().map { Category(it.id, it.name) }
    }

    override suspend fun buyProduct(productId: Int, eventId: Int): BudgetPlanItemResponse {
        return apiService.buyProduct(productId, eventId)
    }

    override suspend fun deleteBudgetItem(itemId: Int) {
        apiService.deleteBudgetItem(itemId)
    }

    override suspend fun addAvailableItem(
        eventId: Int,
        skebobId: Int,
        skebobType: String,
        categoryId: Int
    ) {
        apiService.addAvailableItem(
            eventId,
            AddAvailableItemRequest(
                skebobId = skebobId,
                skebobCategoryId = categoryId,
                skebobType = skebobType
            )
        )
    }
}