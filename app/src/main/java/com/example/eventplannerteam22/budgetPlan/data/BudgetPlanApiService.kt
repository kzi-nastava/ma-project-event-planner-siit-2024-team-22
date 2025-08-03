package com.example.eventplannerteam22.budgetPlan.data

import retrofit2.http.*

interface BudgetPlanApiService {
    @POST("events/{eventId}/budget-plan")
    suspend fun createBudgetPlan(@Path("eventId") eventId: Int): BudgetPlanResponse

    @GET("events/{eventId}/budget-plan")
    suspend fun getBudgetPlan(@Path("eventId") eventId: Int): BudgetPlanResponse

    @GET("product-categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("events/{eventId}/budget-plan/items/add-available")
    suspend fun addAvailableItem(
        @Path("eventId") eventId: Int,
        @Body request: AddAvailableItemRequest
    )

    @POST("products/buy/{productId}/{eventId}")
    suspend fun buyProduct(
        @Path("productId") productId: Int,
        @Path("eventId") eventId: Int
    ): BudgetPlanItemResponse

    @DELETE("budget-items/{itemId}")
    suspend fun deleteBudgetItem(@Path("itemId") itemId: Int)
}

data class AddAvailableItemRequest(
    val skebobId: Int,
    val skebobCategoryId: Int,
    val skebobType: String
)