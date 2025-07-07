package com.example.eventplannerteam22.solutions

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.session.SessionViewModel
import com.example.eventplannerteam22.session.UserRole

@Composable
fun SolutionsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: SolutionsViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val solutions = viewModel.solutions
    val isLoading = viewModel.isLoading
    val hasMoreSolutions = viewModel.hasMoreSolutions
    val session = sessionViewModel.session.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(solutions) { solution ->
                    SolutionCard(solution = solution, navController = navController)
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (hasMoreSolutions) {
                Button(
                    onClick = { viewModel.loadSolutions() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Load More")
                }
            }
        }
        if (session.value.loggedIn && session.value.userRole == UserRole.Supplier)
            FloatingActionButton(
                onClick = { navController.navigate("add_solution") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Solution")
            }
    }
}

@Composable
fun SolutionCard(
    solution: Solution,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("solutions/${solution.id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = solution.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Category type: ${solution.category.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: ${solution.price}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Discount: ${solution.discount}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = solution.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}