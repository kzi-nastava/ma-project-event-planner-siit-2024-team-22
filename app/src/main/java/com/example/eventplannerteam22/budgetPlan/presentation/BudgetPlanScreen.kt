package com.example.eventplannerteam22.budgetPlan.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.products.domain.ProductListItem
import com.example.eventplannerteam22.solutions.domain.Solution

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetPlanScreen(
    eventId: Int,
    viewModel: BudgetPlanViewModel = hiltViewModel(),
    onDetailsClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(eventId) {
        viewModel.init(eventId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Бюджет мероприятия") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddItemDialog() }) {
                Icon(Icons.Default.Edit, contentDescription = "Add item")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text(
                    text = state.error ?: "Ошибка",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Общий бюджет")
                            Text(
                                "₽${state.totalBudget.setScale(2)}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    if (viewModel.showAddItemDialog.value) {
                        AddItemDialog(
                            viewModel = viewModel,
                            onDismiss = { viewModel.hideAddItemDialog() },
                            onConfirm = { viewModel.addSelectedItem() }
                        )
                    }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(state.budgetItems) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(item.categoryName)
                                        Text("₽${item.plannedAmount.setScale(2)}")
                                    }

                                    item.skebobIds.forEach { skebob ->
                                        Button(
                                            onClick = { viewModel.buyProduct(skebob.id) },
                                            enabled = skebob.status != "purchased"
                                        ) {
                                            Text(if (skebob.status == "purchased") "Куплено" else "Купить")
                                        }
                                    }

                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        IconButton(onClick = onDetailsClick) {
                                            Icon(Icons.Default.Info, "Детали")
                                        }
                                        if (item.canDelete) {
                                            IconButton(onClick = { viewModel.deleteItem(item.id) }) {
                                                Icon(Icons.Default.Delete, "Удалить")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddItemDialog(
    viewModel: BudgetPlanViewModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Item to Budget") },
        text = {
            Column {
                // Выбор типа элемента
                Row {
                    FilterChip(
                        selected = viewModel.selectedItemType.value == ItemType.PRODUCT,
                        onClick = { viewModel.selectItemType(ItemType.PRODUCT) },
                        label = { Text("Products") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = viewModel.selectedItemType.value == ItemType.SOLUTION,
                        onClick = { viewModel.selectItemType(ItemType.SOLUTION) },
                        label = { Text("Services") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Список элементов в зависимости от выбранного типа
                when (viewModel.selectedItemType.value) {
                    ItemType.PRODUCT -> ProductList(
                        products = viewModel.products.value,
                        selectedProduct = viewModel.selectedProduct.value,
                        onProductSelected = { viewModel.selectProduct(it) }
                    )
                    ItemType.SOLUTION -> SolutionList(
                        solutions = viewModel.solutions.value,
                        selectedSolution = viewModel.selectedSolution.value,
                        onSolutionSelected = { viewModel.selectSolution(it) }
                    )
                    else -> Text("Please select item type")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = (viewModel.selectedItemType.value == ItemType.PRODUCT && viewModel.selectedProduct.value != null) ||
                        (viewModel.selectedItemType.value == ItemType.SOLUTION && viewModel.selectedSolution.value != null)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ProductList(
    products: List<Product>,
    selectedProduct: Product?,
    onProductSelected: (Product) -> Unit
) {
    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (product == selectedProduct) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.name, fontWeight = FontWeight.Bold)
                        Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(onClick = { onProductSelected(product) }) {
                        Text("Select")
                    }
                }
            }
        }
    }
}

@Composable
fun SolutionList(
    solutions: List<Solution>,
    selectedSolution: Solution?,
    onSolutionSelected: (Solution) -> Unit
) {
    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
        items(solutions) { solution ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (solution == selectedSolution) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(solution.name, fontWeight = FontWeight.Bold)
                        Text("$${solution.price}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(onClick = { onSolutionSelected(solution) }) {
                        Text("Select")
                    }
                }
            }
        }
    }
}