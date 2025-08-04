package com.example.eventplannerteam22.favorites.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.session.SessionViewModel

import com.example.eventplannerteam22.favorites.presentation.FavoriteViewModel
import com.example.eventplannerteam22.router.Screen

@Composable

fun FavoriteScreen(
    sessionViewModel: SessionViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()

) {
    val session by sessionViewModel.session.collectAsState()
    val userId = session.userId


    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.loadFavorites(userId)
        }
    }

    val eventList by viewModel.favoriteEvents.collectAsState()
    val productList by viewModel.favoriteProducts.collectAsState()
    val solutionList by viewModel.favoriteSolutions.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Events", "Products", "Solutions")

    Column(modifier = modifier
        .padding(top = 10.dp, start = 16.dp, end = 16.dp)
        .fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }) {
                    Text(text = title, modifier = Modifier.padding(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            0 -> FavoriteEventList(
                events = eventList,
                onEventClick = { eventId -> navController.navigate(Screen.EventDetails.createRoute(eventId)) },
                onRemoveFavorite = { eventId ->
                    if (userId != null) viewModel.removeEventFromFavorites(userId, eventId)
                }
            )
            1 -> FavoriteProductList(
                products = productList,
                onProductClick = { productId -> navController.navigate(Screen.ProductDetails.createRoute(productId)) },
                onRemoveFavorite = { productId ->
                    if (userId != null) viewModel.removeProductFromFavorites(userId, productId)
                }
            )
            2 -> FavoriteSolutionList(
                solutions = solutionList,
                onSolutionClick = { solutionId -> navController.navigate("solution_details/$solutionId") },
                onRemoveFavorite = { solutionId ->
                    if (userId != null) viewModel.removeSolutionFromFavorites(userId, solutionId)
                }
            )
        }
    }
}