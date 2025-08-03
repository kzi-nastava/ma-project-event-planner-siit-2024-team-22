package com.example.eventplannerteam22.priceList.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.priceList.domen.PriceListItem
import com.example.eventplannerteam22.session.SessionViewModel
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceListScreen(
    modifier: Modifier = Modifier,
    viewModel: PriceListViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val state by viewModel.state.collectAsState()
    val session = sessionViewModel.session.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Price list") },
                navigationIcon = {
                    IconButton(onClick = { /* Навигация назад */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text(
                    text = state.error!!,
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> PriceListContent(
                    items = state.items,
                    onUpdateItem = viewModel::updateItem
                )
            }
        }
    }
}

@Composable
fun PriceListContent(
    items: List<PriceListItem>,
    onUpdateItem: (Int, BigDecimal, BigDecimal) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(items) { index, item ->
            PriceListItemCard(
                item = item,
                onPriceChange = { newPrice ->
                    onUpdateItem(index, newPrice, item.discount)
                },
                onDiscountChange = { newDiscount ->
                    onUpdateItem(index, item.price, newDiscount)
                }
            )
        }
    }
}

@Composable
fun PriceListItemCard(
    item: PriceListItem,
    onPriceChange: (BigDecimal) -> Unit,
    onDiscountChange: (BigDecimal) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var tempPrice by remember { mutableStateOf(item.price.toString()) }
    var tempDiscount by remember { mutableStateOf(item.discount.toString()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Price: ${item.price}")
                    Text("Discount: ${item.discount}%")
                    Text("Discounted: ${item.discountedPrice}", fontWeight = FontWeight.Bold)
                }

                Text(item.type)
            }

            Button(
                onClick = { showEditDialog = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Edit")
            }
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit ${item.name}") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempPrice,
                        onValueChange = { tempPrice = it },
                        label = { Text("Price") }
                    )
                    OutlinedTextField(
                        value = tempDiscount,
                        onValueChange = { tempDiscount = it },
                        label = { Text("Discount (%)") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    try {
                        onPriceChange(BigDecimal(tempPrice))
                        onDiscountChange(BigDecimal(tempDiscount))
                        showEditDialog = false
                    } catch (e: Exception) {
                        // Обработка ошибки
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}