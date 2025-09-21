package com.example.eventplannerteam22.solutions

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.eventplannerteam22.solutions.domain.Solution
import com.example.eventplannerteam22.solutions.presentation.filters.SolutionFilterComponent

@Composable
fun SolutionsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: SolutionsViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val solutions = if (viewModel.isFiltered) viewModel.filteredSolutions else viewModel.solutions
    val isLoading = viewModel.isLoading
    val hasMoreSolutions = viewModel.hasMoreSolutions
    val session = sessionViewModel.session.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Services",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            HorizontalDivider()
            
            // Фильтр сервисов
            SolutionFilterComponent(
                filterState = viewModel.filterState,
                onFilterChange = { filterState -> viewModel.updateFilterState(filterState) },
                onClearFilters = { viewModel.clearFilters() },
                onApplyFilters = { viewModel.applyFilters() },
                modifier = Modifier.padding(16.dp)
            )
            
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
            } else if (hasMoreSolutions && !viewModel.isFiltered) {
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