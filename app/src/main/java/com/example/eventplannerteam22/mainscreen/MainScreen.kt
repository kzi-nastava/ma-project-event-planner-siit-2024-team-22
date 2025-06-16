package com.example.eventplannerteam22.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.events.Event
import com.example.eventplannerteam22.events.EventsViewModel
import com.example.eventplannerteam22.products.Product
import com.example.eventplannerteam22.products.ProductsViewModel
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.solutions.Solution
import com.example.eventplannerteam22.solutions.SolutionsViewModel


@Composable
fun MainScreen(navController: NavController, paddingValues: PaddingValues) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val eventsViewModel: EventsViewModel = hiltViewModel()
    val solutionsViewModel: SolutionsViewModel = hiltViewModel()


    val products = productsViewModel.products.take(3)
    val events = eventsViewModel.events.take(3)
    val solutions = solutionsViewModel.solutions.take(3)

    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {

                Text("Products", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(16.dp))
            }
            items(products) { product ->
                ProductCard(product = product, navController = navController)
            }
            item {
                Button(
                    onClick = { navController.navigate("products_screen") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(text = "View All Products")
                }
            }

            item {

                Text("Events", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(16.dp))
            }
            items(events) { event ->
                EventCard(event = event, navController = navController)
            }
            item {
                Button(
                    onClick = { navController.navigate("events_screen") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(text = "View All Events")
                }
            }

            item {

                Text("Solutions", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(16.dp))
            }
            items(solutions) { solution ->
                SolutionCard(solution = solution, navController = navController)
            }
            item {
                Button(
                    onClick = { navController.navigate(Screen.ServicesScreen.route) },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(text = "View All Solutions")
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            navController.navigate("products/${product.id}")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: ${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    Card(
        // подумать как сделать это по красивее
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable {
            navController.navigate("events/${event.id}")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${event.dateOfEvent}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SolutionCard(solution: Solution, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            navController.navigate("solutions/${solution.id}")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = solution.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Category: ${solution.category.name}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}