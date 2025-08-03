package com.example.eventplannerteam22.budgetPlan.presentation.model

import androidx.compose.runtime.Immutable
import java.math.BigDecimal

@Immutable
data class BudgetPlanState(
    val eventId: Int = 0,
    val budgetItems: List<BudgetPlanItemUi> = emptyList(),
    val categories: List<Category> = emptyList(),
    val totalBudget: BigDecimal = BigDecimal.ZERO,
    val isLoading: Boolean = false,
    val error: String? = null
)

@Immutable
data class BudgetPlanItemUi(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val plannedAmount: BigDecimal,
    val skebobIds: List<SkebobEntry> = emptyList(),
    val canDelete: Boolean = true
)

@Immutable
data class Category(
    val id: Int,
    val name: String
)

@Immutable
data class SkebobEntry(
    val id: Int,
    val categoryId: Int,
    val status: String
)