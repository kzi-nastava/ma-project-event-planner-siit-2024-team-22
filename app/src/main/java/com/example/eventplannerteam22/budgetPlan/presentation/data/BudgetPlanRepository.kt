package com.example.eventplannerteam22.budgetPlan.presentation.data

import com.example.eventplannerteam22.budgetPlan.presentation.model.Category

interface BudgetPlanRepository {
    suspend fun createBudgetPlan(eventId: Int): BudgetPlanResponse
    suspend fun getBudgetPlan(eventId: Int): BudgetPlanResponse
    suspend fun getCategories(): List<Category>
    suspend fun buyProduct(productId: Int, eventId: Int): BudgetPlanItemResponse
    suspend fun deleteBudgetItem(itemId: Int)
    suspend fun addAvailableItem(
        eventId: Int,
        skebobId: Int,
        skebobType: String,
        categoryId: Int
    )
}