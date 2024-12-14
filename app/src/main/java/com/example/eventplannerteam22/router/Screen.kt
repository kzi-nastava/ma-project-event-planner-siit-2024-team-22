package com.example.eventplannerteam22.router

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object LoginScreen : Screen("login_screen")
    object RegistrationScreen : Screen("registration_screen")
    object EventsScreen : Screen("events_screen")
    object ProductsScreen : Screen("products_screen")
    object ServicesScreen : Screen("services_screen")
    object ServicesDetailScreen : Screen("service_detail_screen/{serviceId}")
    object ProfileScreen : Screen("profile_screen")
    object SplashScreen : Screen("splash_screen")
}