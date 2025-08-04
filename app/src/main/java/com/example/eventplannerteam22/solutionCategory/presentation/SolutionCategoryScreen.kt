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
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Dialog states
    var showCreateDialog by rememberSaveable { mutableStateOf(false) }
    var showEditDialog by rememberSaveable { mutableStateOf(false) }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var newCategoryType by rememberSaveable { mutableStateOf("") }

    // Handle edit dialog visibility
    LaunchedEffect(selectedCategory) {
        selectedCategory?.let {
            newCategoryName = it.name ?: ""
            newCategoryType = it.categoryType ?: ""
            showEditDialog = true
        }
    }

    // Handle snackbar messages
    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "Retry"
            )
            // Clear error after showing
            viewModel.retry()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Solution Categories",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    newCategoryName = ""
                    newCategoryType = ""
                    showCreateDialog = true
                }
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
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.categories.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No categories found")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    SolutionCategoryList(
                        categories = state.categories,
                        onEditClick = { viewModel.setSelectedCategory(it) },
                        onDeleteClick = { viewModel.deleteSolutionCategory(it) }
                    )
                }
            }

            // Create Dialog
            if (showCreateDialog) {
                CategoryEditDialog(
                    title = "Create New Category",
                    currentName = newCategoryName,
                    currentType = newCategoryType,
                    onNameChange = { newCategoryName = it },
                    onTypeChange = { newCategoryType = it },
                    onConfirm = {
                        viewModel.addSolutionCategory(
                            SolutionCategory(
                                id = 0, // Will be assigned by backend
                                name = newCategoryName,
                                categoryType = newCategoryType
                            )
                        )
                        showCreateDialog = false
                    },
                    onDismiss = { showCreateDialog = false }
                )
            }

            // Edit Dialog
            if (showEditDialog && selectedCategory != null) {
                CategoryEditDialog(
                    title = "Edit Category",
                    currentName = newCategoryName,
                    currentType = newCategoryType,
                    onNameChange = { newCategoryName = it },
                    onTypeChange = { newCategoryType = it },
                    onConfirm = {
                        viewModel.updateSolutionCategory(
                            selectedCategory!!.copy(
                                name = if (newCategoryName.isBlank()) selectedCategory!!.name else newCategoryName,
                                categoryType = if (newCategoryType.isBlank()) selectedCategory!!.categoryType else newCategoryType
                            ),
                            id = selectedCategory!!.id
                        )
                        showEditDialog = false
                    },
                    onDismiss = { showEditDialog = false }
                )
            }
        }
    }
}

@Composable
private fun SolutionCategoryList(
    categories: List<SolutionCategory>,
    onEditClick: (SolutionCategory) -> Unit,
    onDeleteClick: (Int) -> Unit,  // Changed to accept Int (category ID)
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            SolutionCategoryItem(
                category = category,
                onEditClick = { onEditClick(category) },
                onDeleteClick = { onDeleteClick(category.id) }  // Passing ID here
            )
        }
    }
}

@Composable
private fun SolutionCategoryItem(
    category: SolutionCategory,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,  // No parameters needed here
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = category.name ?: "Unnamed Category",
                style = MaterialTheme.typography.titleMedium
            )

            category.categoryType?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Type: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryEditDialog(
    title: String,
    currentName: String,
    currentType: String,
    onNameChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = currentName,
                onValueChange = onNameChange,
                label = { Text("Name (leave blank to keep current)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = currentType,
                onValueChange = onTypeChange,
                label = { Text("Type (leave blank to keep current)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                enabled = currentName.isNotBlank() || currentType.isNotBlank()
            ) {
                Text("Confirm")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}