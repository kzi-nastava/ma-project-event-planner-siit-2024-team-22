package com.example.eventplannerteam22.router

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventplannerteam22.auth.login.LoginScreen
import com.example.eventplannerteam22.auth.registration.RegistrationScreen
import com.example.eventplannerteam22.events.AddEventScreen
import com.example.eventplannerteam22.events.EventDetailScreen
import com.example.eventplannerteam22.presentation.MainLayout
import com.example.eventplannerteam22.presentation.screens.SplashScreen
import com.example.eventplannerteam22.products.AddProductScreen
import com.example.eventplannerteam22.products.ProductDetailScreen
import com.example.eventplannerteam22.profile.ProfileScreen
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.solutions.AddSolutionScreen
import com.example.eventplannerteam22.solutions.SolutionDetailScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        // Splash Screen (No MainLayout)
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }

        // Other screens (wrapped in MainLayout)
        composable(route = Screen.MainScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                com.example.eventplannerteam22.mainscreen.MainScreen(navController, paddingValues)
            }
        }

        composable(route = Screen.LoginScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                LoginScreen(navController, paddingValues)
            }
        }

        composable(route = Screen.RegistrationScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                RegistrationScreen(navController, paddingValues)
            }
        }

        composable(route = Screen.EventsScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                com.example.eventplannerteam22.events.EventsScreen(navController, paddingValues)
            }
        }

        composable(route = Screen.ProductsScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                com.example.eventplannerteam22.products.ProductsScreen(navController, paddingValues)
            }
        }

        composable(route = "add_product") {
            AddProductScreen(navController)
        }
        composable("products/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                ProductDetailScreen(productId = productId, navController = navController)
            }
        }
        composable("solutions/{solutionId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("solutionId")?.toIntOrNull()
            if (productId != null) {
                SolutionDetailScreen(solutionId = productId, navController = navController)
            }
        }
        composable("events/{eventId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull()
            if (productId != null) {
                EventDetailScreen(eventId = productId, navController = navController)
            }
        }

        composable("add_event") {
            AddEventScreen(navController)
        }

        composable("add_solution") {
            AddSolutionScreen(navController)
        }

        composable(route = Screen.ServicesScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                com.example.eventplannerteam22.solutions.SolutionsScreen(
                    navController,
                    paddingValues
                )
            }
        }

        composable(route = Screen.ProfileScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                ProfileScreen(navController, coroutineScope, paddingValues)
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    Column {
        TextButton(
            onClick = {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
                coroutineScope.launch { drawerState.close() }
            }
        ) { Text("Main") }

        TextButton(
            onClick = {
                navController.navigate(Screen.EventsScreen.route)
                coroutineScope.launch { drawerState.close() }
            }
        ) { Text("Events") }

        TextButton(
            onClick = {
                navController.navigate(Screen.ProductsScreen.route)
                coroutineScope.launch { drawerState.close() }
            }
        ) { Text("Products") }

        TextButton(
            onClick = {
                navController.navigate(Screen.ServicesScreen.route)
                coroutineScope.launch { drawerState.close() }
            }
        ) { Text("Services") }

        if (!sessionViewModel.session.collectAsState().value.loggedIn) {
            TextButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                    coroutineScope.launch { drawerState.close() }
                }
            ) { Text("Login") }

            TextButton(
                onClick = {
                    navController.navigate(Screen.RegistrationScreen.route)
                    coroutineScope.launch { drawerState.close() }
                }
            ) { Text("Register") }
        }
    }
}


