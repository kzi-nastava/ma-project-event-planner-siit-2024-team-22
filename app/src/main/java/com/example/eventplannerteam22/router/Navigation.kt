package com.example.eventplannerteam22.router

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventplannerteam22.presentation.screens.LoginScreen
import com.example.eventplannerteam22.presentation.MainLayout
import com.example.eventplannerteam22.presentation.screens.EventsScreen
import com.example.eventplannerteam22.presentation.screens.MainScreen
import com.example.eventplannerteam22.presentation.screens.ProductsScreen
import com.example.eventplannerteam22.presentation.screens.ProfileScreen
import com.example.eventplannerteam22.presentation.screens.RegistrationScreen
import com.example.eventplannerteam22.presentation.screens.ServicesScreen
import com.example.eventplannerteam22.presentation.screens.SplashScreen
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
                MainScreen(navController, paddingValues)
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
                EventsScreen(navController, paddingValues)
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
                ProductsScreen(navController, paddingValues)
            }
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
                ServicesScreen(navController, paddingValues)
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
                ProfileScreen(navController, paddingValues)
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
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


