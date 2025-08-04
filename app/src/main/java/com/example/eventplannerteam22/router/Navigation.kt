package com.example.eventplannerteam22.router

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventplannerteam22.R
import com.example.eventplannerteam22.auth.AuthScreen
import com.example.eventplannerteam22.auth.login.LoginScreen
import com.example.eventplannerteam22.auth.registration.RegistrationScreen
import com.example.eventplannerteam22.budgetPlan.presentation.BudgetPlanScreen
import com.example.eventplannerteam22.eventType.presentation.createeventtype.CreateEventType
import com.example.eventplannerteam22.eventType.presentation.eventtypelist.EventTypesScreen
import com.example.eventplannerteam22.events.presentation.addevent.AddEventScreen
import com.example.eventplannerteam22.events.presentation.eventdetails.EventDetailScreen
import com.example.eventplannerteam22.events.presentation.eventlist.EventsScreen
import com.example.eventplannerteam22.mainscreen.MainScreen
import com.example.eventplannerteam22.presentation.MainLayout
import com.example.eventplannerteam22.presentation.screens.SplashScreen
import com.example.eventplannerteam22.products.presentation.createproduct.CreateProductScreen
import com.example.eventplannerteam22.products.presentation.productdetails.ProductDetailScreen
import com.example.eventplannerteam22.products.presentation.productlist.ProductsScreen
import com.example.eventplannerteam22.profile.presentation.editprofile.EditProfileScreen
import com.example.eventplannerteam22.profile.presentation.profile.ProfileScreen
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.solutions.presentation.addSolution.AddSolutionScreen
import com.example.eventplannerteam22.solutions.presentation.editSolution.EditSolutionScreen
import com.example.eventplannerteam22.solutions.presentation.viewSolutionDetail.SolutionDetailScreen
import com.example.eventplannerteam22.solutions.presentation.viewSolution.SolutionsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen (No MainLayout)
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }

        // Other screens (wrapped in MainLayout)
        composable(route = Screen.Main.route) {
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

        composable(route = Screen.Auth.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues -> AuthScreen(navController, paddingValues) }
        }

        composable(route = Screen.Login.route) {
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

        composable(route = Screen.Registration.route) {
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

        composable(route = Screen.Events.route) {
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

        composable(route = Screen.Products.route) {
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

        composable(route = Screen.CreateProduct.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                CreateProductScreen(paddingValues)
            }
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

        composable("edit_solution/{solutionId}") { backStackEntry ->
            val solutionId = backStackEntry.arguments?.getString("solutionId")?.toIntOrNull()
            if (solutionId != null) {
                EditSolutionScreen(
                    solutionId = solutionId,
                    navController = navController
                )
            }
        }

        composable(
            route = "edit_solution/{solutionId}",
            arguments = listOf(navArgument("solutionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val solutionId = backStackEntry.arguments?.getInt("solutionId") ?: 0
            EditSolutionScreen(
                solutionId = solutionId,
                navController = navController
            )
        }

        composable(route = Screen.Services.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                SolutionsScreen(
                    navController,
                    paddingValues
                )
            }
        }

        composable(route = Screen.Profile.route) {
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

        composable(route = Screen.EditProfile.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                EditProfileScreen(navController = navController)
            }
        }

        composable(route = Screen.EventTypes.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                EventTypesScreen(
                    paddingValues = paddingValues,
                    navController = navController
                )
            }
        }

        composable(route = Screen.CreateEventType.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                CreateEventType(
                    paddingValues = paddingValues,
                    navController = navController
                )

            }
        }

        composable(route = Screen.BudgetPlanScreen.route) { backStackEntry ->
//            val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull() ?: 0
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) {
                BudgetPlanScreen(
                    eventId = 1,
                    onDetailsClick = { /* Handle details navigation if needed */ },
//                    modifier = Modifier.padding(paddingValues)
                )
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
        Text(
            text = "Event Planner",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider()
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.Main.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Main menu") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_24px),
                    contentDescription = "Main menu"
                )
            }
        )
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.Events.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Event list") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.event_list_24px),
                    contentDescription = "Event list"
                )
            }
        )
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.Products.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Products list") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.shopping_bag_24px),
                    contentDescription = "Product list"
                )
            }
        )
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.Services.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Service list") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_repair_service_24px),
                    contentDescription = "Service list"
                )
            }
        )
        HorizontalDivider()
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.EventTypes.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Event type management") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_repair_service_24px),
                    contentDescription = "Event type management"
                )
            }
        )
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.BudgetPlanScreen.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Event budget") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_repair_service_24px),
                    contentDescription = "Budget generation"
                )
            }
        )
    }
}


