// data/api/model/BudgetPlanResponse.kt
package com.example.eventplannerteam22.budgetPlan.presentation.data

import com.example.eventplannerteam22.budgetPlan.presentation.model.SkebobEntry
import java.math.BigDecimal

data class BudgetPlanResponse(
    val id: Int,
    val eventId: Int,
    val items: List<BudgetPlanItemResponse>
)

data class BudgetPlanItemResponse(
    val id: Int,
    val plannedAmount: BigDecimal,
    val skebobCategoryId: Int,
    val skebobIds: List<SkebobEntry>
)

data class CategoryResponse(
    val id: Int,
    val name: String
)