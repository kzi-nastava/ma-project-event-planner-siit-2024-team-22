package com.example.eventplannerteam22.router

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    MainLayout(
        drawerState = drawerState,
        coroutineScope = coroutineScope,
        drawerContent = {
            Column {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.MainScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Main")}

                TextButton(
                    onClick = {
                        navController.navigate(Screen.EventsScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Events")}

                TextButton(
                    onClick = {
                        navController.navigate(Screen.ProductsScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Products")}

                TextButton(
                    onClick = {
                        navController.navigate(Screen.ServicesScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Services")}

                TextButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Login")}
                TextButton(
                    onClick = {
                        navController.navigate(Screen.RegistrationScreen.route)
                        coroutineScope.launch() {
                            drawerState.close()
                        }
                    },
                ) {Text("Register")}
            }
        },
        onProfileClick = {
            navController.navigate(Screen.ProfileScreen.route)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.MainScreen.route) {
                MainScreen(navController)
            }
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController)
            }
            composable(route = Screen.RegistrationScreen.route){
                RegistrationScreen(navController)
            }
            composable(route = Screen.EventsScreen.route){
                EventsScreen(navController)
            }
            composable(route = Screen.ProductsScreen.route){
                ProductsScreen(navController)
            }
            composable(route = Screen.ServicesScreen.route){
                ServicesScreen(navController)
            }
            composable(route = Screen.ProfileScreen.route){
                ProfileScreen(navController)
            }

            composable(route = Screen.SplashScreen.route){
                SplashScreen(navController)
            }
        }
    }
}
