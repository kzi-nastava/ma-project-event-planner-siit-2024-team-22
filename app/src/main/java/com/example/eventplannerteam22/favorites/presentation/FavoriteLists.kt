package com.example.eventplannerteam22.favorites.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.solutions.domain.Solution

@Composable
fun FavoriteEventList(
    events: List<Event>,
    onEventClick: (Int) -> Unit = {},
    onRemoveFavorite: ((Int) -> Unit)? = null
) {
    if (events.isEmpty()) {
        Text("No favorite events yet", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(events) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onEventClick(event.id) }
                        ) {
                            Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
                        }

                        if (onRemoveFavorite != null) {
                            IconButton(onClick = { onRemoveFavorite(event.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove from favorites"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteProductList(
    products: List<Product>,
    onProductClick: (Int) -> Unit = {},
    onRemoveFavorite: ((Int) -> Unit)? = null
) {
    if (products.isEmpty()) {
        Text("No favorite products yet", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onProductClick(product.id) }
                        ) {
                            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = product.description ?: "",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        if (onRemoveFavorite != null) {
                            IconButton(onClick = { onRemoveFavorite(product.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove from favorites"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteSolutionList(
    solutions: List<Solution>,
    onSolutionClick: (Int) -> Unit = {},
    onRemoveFavorite: ((Int) -> Unit)? = null
) {
    if (solutions.isEmpty()) {
        Text("No favorite solutions yet", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(solutions) { solution ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSolutionClick(solution.id) }
                        ) {
                            Text(text = solution.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = solution.description ?: "",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        if (onRemoveFavorite != null) {
                            IconButton(onClick = { onRemoveFavorite(solution.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove from favorites"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}