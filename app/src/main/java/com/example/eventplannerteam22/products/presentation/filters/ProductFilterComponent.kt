package com.example.eventplannerteam22.products.presentation.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductFilterComponent(
    filterState: ProductFilterState,
    onFilterChange: (ProductFilterState) -> Unit,
    onClearFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val productFilterOptions = listOf(
        "name" to "Name",
        "description" to "Description",
        "category" to "Category",
        "price" to "Price",
        "discount" to "Discount",
        "isPrivate" to "Private"
    )

    val showCategoryDropdown = filterState.selectedFilter == "Category"

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Product Filters",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Первая строка фильтров
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Выпадающее меню для выбора типа фильтра
                ExposedDropdownMenuBox(
                    expanded = filterState.isExpanded,
                    onExpandedChange = { 
                        onFilterChange(filterState.copy(isExpanded = !filterState.isExpanded))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = filterState.selectedFilter,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Filter Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = filterState.isExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = filterState.isExpanded,
                        onDismissRequest = { 
                            onFilterChange(filterState.copy(isExpanded = false))
                        }
                    ) {
                        productFilterOptions.forEach { (key, displayName) ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(displayName) },
                                onClick = {
                                    onFilterChange(filterState.copy(
                                        selectedFilter = displayName,
                                        isExpanded = false
                                    ))
                                }
                            )
                        }
                    }
                }
                
                // Поле ввода для значения фильтра или выпадающее меню для приватности
                if (filterState.selectedFilter == "Private") {
                    ExposedDropdownMenuBox(
                        expanded = filterState.isPrivateExpanded,
                        onExpandedChange = { 
                            onFilterChange(filterState.copy(isPrivateExpanded = !filterState.isPrivateExpanded))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = getFilterValue(filterState),
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Value") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = filterState.isPrivateExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            enabled = filterState.selectedFilter.isNotEmpty()
                        )
                        
                        ExposedDropdownMenu(
                            expanded = filterState.isPrivateExpanded,
                            onDismissRequest = { 
                                onFilterChange(filterState.copy(isPrivateExpanded = false))
                            }
                        ) {
                            listOf("Yes", "No").forEach { option ->
                                androidx.compose.material3.DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onFilterChange(updateFilterValue(filterState, option).copy(isPrivateExpanded = false))
                                    }
                                )
                            }
                        }
                    }
                } else if (filterState.selectedFilter == "Price") {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OutlinedTextField(
                            value = filterState.minPrice,
                            onValueChange = { newValue ->
                                onFilterChange(filterState.copy(minPrice = newValue.filter { it.isDigit() || it == '.' }))
                            },
                            label = { Text("Min Price") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = filterState.maxPrice,
                            onValueChange = { newValue ->
                                onFilterChange(filterState.copy(maxPrice = newValue.filter { it.isDigit() || it == '.' }))
                            },
                            label = { Text("Max Price") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else if (filterState.selectedFilter == "Discount") {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OutlinedTextField(
                            value = filterState.minDiscount,
                            onValueChange = { newValue ->
                                onFilterChange(filterState.copy(minDiscount = newValue.filter { it.isDigit() || it == '.' }))
                            },
                            label = { Text("Min Discount") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = filterState.maxDiscount,
                            onValueChange = { newValue ->
                                onFilterChange(filterState.copy(maxDiscount = newValue.filter { it.isDigit() || it == '.' }))
                            },
                            label = { Text("Max Discount") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else if (showCategoryDropdown) {
                    ExposedDropdownMenuBox(
                        expanded = filterState.isCategoryExpanded,
                        onExpandedChange = { 
                            onFilterChange(filterState.copy(isCategoryExpanded = !filterState.isCategoryExpanded))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = getFilterValue(filterState),
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Category") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = filterState.isCategoryExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            enabled = filterState.selectedFilter.isNotEmpty()
                        )

                        if (filterState.selectedFilter == "Category") {
                            ExposedDropdownMenu(
                                expanded = filterState.isCategoryExpanded,
                                onDismissRequest = { 
                                    onFilterChange(filterState.copy(isCategoryExpanded = false))
                                }
                            ) {
                                filterState.categories.forEach { category ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { 
                                            androidx.compose.material3.Text(category.name)
                                        },
                                        onClick = {
                                            onFilterChange(filterState.copy(
                                                selectedCategory = category,
                                                isCategoryExpanded = false
                                            ))
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = getFilterValue(filterState),
                        onValueChange = { newValue ->
                            onFilterChange(updateFilterValue(filterState, newValue))
                        },
                        label = { Text("Value") },
                        modifier = Modifier.weight(1f),
                        enabled = filterState.selectedFilter.isNotEmpty()
                    )
                }
                
                // Кнопка очистки
                IconButton(
                    onClick = onClearFilters
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear Filters"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Кнопка применения фильтров
            Button(
                onClick = onApplyFilters,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply Filters")
            }
        }
    }
}

private fun getFilterValue(filterState: ProductFilterState): String {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.name
        "Description" -> filterState.description
        "Category" -> filterState.selectedCategory?.name ?: "Select Category"
        "Price" -> "${filterState.minPrice}-${filterState.maxPrice}"
        "Discount" -> "${filterState.minDiscount}-${filterState.maxDiscount}"
        "Private" -> filterState.isPrivate
        else -> ""
    }
}

private fun updateFilterValue(filterState: ProductFilterState, newValue: String): ProductFilterState {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.copy(name = newValue)
        "Description" -> filterState.copy(description = newValue)
        "Price" -> filterState.copy(minPrice = newValue.filter { it.isDigit() || it == '.' })
        "Discount" -> filterState.copy(minDiscount = newValue.filter { it.isDigit() || it == '.' })
        "Private" -> filterState.copy(isPrivate = newValue)
        else -> filterState
    }
}
