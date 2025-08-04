package com.example.eventplannerteam22.solutionCategory.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.R
import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionCategoryScreen(
    viewModel: SolutionCategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showCreateDialog by rememberSaveable { mutableStateOf(false) }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var newCategoryType by rememberSaveable { mutableStateOf("") } // Added for category type

    LaunchedEffect(key1 = true) {
        viewModel.state.collectLatest { currentState ->
            currentState.error?.let { error ->
                snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "Retry"
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Solution categories",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = "Add solution category"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(3.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(3.dp))

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                } else if (state.categories.isEmpty() && !state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No solution categories found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(
                            onClick = { viewModel.retry() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                } else {
                    SolutionCategoryList(
                        categories = state.categories,
                        modifier = Modifier.fillMaxSize(),
                        onEditClick = { /* TODO: Implement edit */ },
                        onDeleteClick = { /* TODO: Implement delete */ }
                    )
                }
            }
        }

        if (showCreateDialog) {
            ModalBottomSheet(
                onDismissRequest = { showCreateDialog = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create New Solution Category",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = newCategoryName,
                        onValueChange = { newCategoryName = it },
                        label = { Text("Category Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newCategoryType,
                        onValueChange = { newCategoryType = it },
                        label = { Text("Category Type") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.addSolutionCategory(
                                SolutionCategory(
                                    id = 0,
                                    name = newCategoryName,
                                    categoryType = newCategoryType
                                )
                            )
                            newCategoryName = ""
                            newCategoryType = ""
                            showCreateDialog = false
                        },
                        enabled = newCategoryName.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Create")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SolutionCategoryList(
    categories: List<SolutionCategory>,
    modifier: Modifier = Modifier,
    onEditClick: (SolutionCategory) -> Unit,
    onDeleteClick: (SolutionCategory) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            SolutionCategoryItem(
                category = category,
                modifier = Modifier.fillMaxWidth(),
                onEditClick = { onEditClick(category) },
                onDeleteClick = { onDeleteClick(category) }
            )
        }
    }
}

@Composable
private fun SolutionCategoryItem(
    category: SolutionCategory,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = { /* Optional: Add click handling if needed */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = category.name ?: "Unnamed category",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text("Delete")
                }
            }
        }
    }
}