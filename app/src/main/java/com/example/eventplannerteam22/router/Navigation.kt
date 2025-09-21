package com.example.eventplannerteam22.router

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.eventplannerteam22.admin.comments.presentation.AdminCommentModerationScreen
import com.example.eventplannerteam22.auth.AuthScreen
import com.example.eventplannerteam22.auth.login.LoginScreen
import com.example.eventplannerteam22.auth.registration.RegistrationScreen
import com.example.eventplannerteam22.budgetPlan.presentation.BudgetPlanScreen
import com.example.eventplannerteam22.chat.presentation.ChatListScreen
import com.example.eventplannerteam22.chat.presentation.ChatScreen
import com.example.eventplannerteam22.events.presentation.addevent.CreateEventScreen
import com.example.eventplannerteam22.events.presentation.editevent.EditEventScreen
import com.example.eventplannerteam22.events.presentation.eventdetails.EventDetailScreen
import com.example.eventplannerteam22.events.presentation.eventlist.EventListScreen
import com.example.eventplannerteam22.eventtype.presentation.createeventtype.CreateEventType
import com.example.eventplannerteam22.eventtype.presentation.eventtypelist.EventTypesScreen
import com.example.eventplannerteam22.mainscreen.MainScreen
import com.example.eventplannerteam22.notifications.NotificationPermissionScreen
import com.example.eventplannerteam22.notifications.presentation.NotificationScreen
import com.example.eventplannerteam22.presentation.MainLayout
import com.example.eventplannerteam22.presentation.screens.SplashScreen
import com.example.eventplannerteam22.priceList.presentation.PriceListScreen
import com.example.eventplannerteam22.productcategory.presentation.ProductCategoryScreen
import com.example.eventplannerteam22.products.presentation.createproduct.CreateProductScreen
import com.example.eventplannerteam22.products.presentation.editproduct.UpdateProductScreen
import com.example.eventplannerteam22.products.presentation.productdetails.ProductDetailScreen
import com.example.eventplannerteam22.products.presentation.productlist.ProductsScreen
import com.example.eventplannerteam22.profile.presentation.editprofile.EditProfileScreen
import com.example.eventplannerteam22.profile.presentation.profile.ProfileScreen
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.session.UserRole
import com.example.eventplannerteam22.solutionCategory.presentation.SolutionCategoryScreen
import com.example.eventplannerteam22.solutions.AddSolutionScreen
import com.example.eventplannerteam22.solutions.SolutionDetailScreen
import com.example.eventplannerteam22.solutions.SolutionsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Block
import com.example.eventplannerteam22.favorites.presentation.FavoriteScreen
import kotlinx.coroutines.selects.select

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
//        startDestination = Screen.Splash.route
        startDestination = Screen.NotificationPermission.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }

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

        composable(route = Screen.BlockedUsers.route) {
            val sessionViewModel: com.example.eventplannerteam22.session.SessionViewModel = androidx.hilt.navigation.compose.hiltViewModel(LocalContext.current as ComponentActivity)
            val session = sessionViewModel.session.collectAsState().value
            val userId = session.userId ?: -1
            val blockedUsersViewModel: com.example.eventplannerteam22.blockedusers.presentation.BlockedUsersViewModel = androidx.hilt.navigation.compose.hiltViewModel()
            val state = blockedUsersViewModel.state.collectAsState().value
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                },
                topBarTitle = "Blocked users"
            ) { paddingValues ->
                com.example.eventplannerteam22.blockedusers.presentation.BlockedUsersScreen(
                    blockedUsers = state.blockedUsers,
                    onBlockUser = { email -> blockedUsersViewModel.blockUserByEmail(email) },
                    onUnblockUser = { id -> blockedUsersViewModel.unblockUser(id) },
                    isLoading = state.isLoading,
                    error = state.error
                )
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
                EventListScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
        }

        composable(route = Screen.CreateEvent.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                CreateEventScreen(navController, paddingValues)
            }
        }

        composable(
            route = Screen.EditEvent.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                EditEventScreen(
                    paddingValues = paddingValues,
                    eventId = eventId,
                    navController = navController
                )
            }

        }

        composable(
            route = Screen.EventDetails.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                EventDetailScreen(
                    paddingValues = paddingValues,
                    eventId = eventId,
                    navController = navController,
                    sessionViewModel = sessionViewModel
                )
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
                CreateProductScreen(navController, paddingValues)
            }
        }
        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                ProductDetailScreen(
                    paddingValues = paddingValues,
                    productId = productId,
                    navController = navController,
                    sessionViewModel = sessionViewModel
                )
            }
        }

        composable(
            route = Screen.UpdateProduct.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable

            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                UpdateProductScreen(
                    productId = productId,
                    paddingValues = paddingValues,
                    navController = navController
                )
            }
        }

        composable("product-categories") {
            ProductCategoryScreen()
        }

        composable("solutions/{solutionId}") { backStackEntry ->
            val solutionId = backStackEntry.arguments?.getString("solutionId")?.toIntOrNull()
            val sessionViewModel: SessionViewModel = hiltViewModel()
            if (solutionId != null) {
                SolutionDetailScreen(
                    solutionId = solutionId,
                    navController = navController,
                    sessionViewModel = sessionViewModel
                )
            }
        }

        composable("solution-categories") {
            SolutionCategoryScreen()
        }

        composable("add_solution") {
            AddSolutionScreen(navController)
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

        composable(route = Screen.PriceListScreen.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                PriceListScreen(modifier = Modifier.padding(paddingValues))
            }
        }
        composable(route = Screen.AdminModeration.route) {
            AdminCommentModerationScreen(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )
        }
        composable("notifications") {
            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
            NotificationScreen(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                sessionViewModel = sessionViewModel
            )
        }

//        composable("favorites") {
//
//            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
//            FavoriteScreen(
//                sessionViewModel = sessionViewModel,
//                navController = navController
//            )
//        }

        composable("favorites") {
            val sessionViewModel = hiltViewModel<SessionViewModel>(LocalContext.current as ComponentActivity)
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = {
                    DrawerContent(navController, coroutineScope, drawerState)
                }
            ) { paddingValues ->
                FavoriteScreen(
                    sessionViewModel = sessionViewModel,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        composable(Screen.NotificationPermission.route) {
            NotificationPermissionScreen(navController)
        }

        composable(route = Screen.ChatList.route) {
            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                ChatListScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("conversationId") { type = NavType.StringType },
                navArgument("receiverId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: return@composable
            val receiverId = backStackEntry.arguments?.getInt("receiverId") ?: return@composable

            MainLayout(
                navController = navController,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                drawerContent = { DrawerContent(navController, coroutineScope, drawerState) }
            ) { paddingValues ->
                ChatScreen(
                    conversationId = conversationId,
                    receiverId = receiverId,
                    modifier = Modifier.padding(paddingValues)
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
    val session by sessionViewModel.session.collectAsState()
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
                navController.navigate(Screen.SolutionCategoriesScreen.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Solution categories") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_repair_service_24px),
                    contentDescription = "Solution categories management"
                )
            }
        )
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.ProductCategoriesScreen.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Product categories") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.home_repair_service_24px),
                    contentDescription = "Product categories management"
                )
            }
        )
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
        if (session.userRole == UserRole.Admin) {
            HorizontalDivider()
            NavigationDrawerItem(
                onClick = {
                    navController.navigate(Screen.AdminModeration.route)
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Moderate Comments") },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.sample_image),
                        contentDescription = "Moderate Comments"
                    )
                }
            )
        }

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
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.PriceListScreen.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Price List") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.event_list_24px),
                    contentDescription = "Price List"
                )
            }
        )
        if (session.loggedIn && session.userId != null) {
            NavigationDrawerItem(
                onClick = {
                    navController.navigate(Screen.BlockedUsers.route)
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Blocked users") },
                icon = {
                    Icon(imageVector = Icons.Default.Block, contentDescription = "Blocked users")
                }
            )
            NavigationDrawerItem(
                onClick = {
                    navController.navigate("notifications")
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Notifications") },
                icon = {
                    Icon(Icons.Default.Notifications, contentDescription = null)
                }
            )
            NavigationDrawerItem(
                onClick = {
                    navController.navigate("favorites")
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Favorites") },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) }
            )
        }
        NavigationDrawerItem(
            onClick = {
                navController.navigate(Screen.ChatList.route)
                coroutineScope.launch { drawerState.close() }
            },
            selected = false,
            label = { Text("Messages") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.edit_24px), // Add your chat icon
                    contentDescription = "Chat"
                )
            }
        )
            NavigationDrawerItem(
                onClick = {
                    navController.navigate("notifications")
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Notifications") },
                icon = {
                    Icon(Icons.Default.Notifications, contentDescription = null)
                }
            )
            NavigationDrawerItem(
                onClick = {
                    navController.navigate("favorites")
                    coroutineScope.launch { drawerState.close() }
                },
                selected = false,
                label = { Text("Favorites") },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) }
            )
        }

    }


