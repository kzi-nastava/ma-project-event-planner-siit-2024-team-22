package com.example.eventplannerteam22.router

sealed class Screen(val route: String) {
    object Main : Screen("main-screen")
    object Auth : Screen("auth-screen")
    object Login : Screen("login-screen")
    object Registration : Screen("registration-screen")
    object Events : Screen("event-screen")

    object EventDetails : Screen("event/{eventId}") {
        fun createRoute(eventId: Int): String = "event/$eventId"
    }

    object CreateEvent : Screen("create-event")
    object Products : Screen("products-screen")
    object ProductDetails : Screen("product/{productId}") {
        fun createRoute(productId: Int): String = "product/$productId"
    }

    object Services : Screen("services-screen")
    object Profile : Screen("profile-screen")
    object EditProfile : Screen("edit-profile-screen")
    object Splash : Screen("splash-screen")
    object EventTypes : Screen("event-type-screen")
    data class EditEventType(val id: Int) : Screen("event-type/${id}")
    object CreateEventType : Screen("create-event-type")
    object AdminModeration : Screen("admin/moderation")

    object BudgetPlanScreen : Screen("budget-plan")
    object CreateProduct : Screen("create-product")
    object PriceListScreen : Screen("price-list")
}