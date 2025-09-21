package com.example.eventplannerteam22.solutions.presentation.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

data class SolutionFilterState(
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val price: String = "",
    val discount: String = "",
    val selectedFilter: String = "",
    val isExpanded: Boolean = false
)

@Composable
fun SolutionFilterComponent(
    filterState: SolutionFilterState,
    onFilterChange: (SolutionFilterState) -> Unit,
    onClearFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val solutionFilterOptions = listOf(
        "name" to "Name",
        "description" to "Description",
        "category" to "Category",
        "price" to "Price",
        "discount" to "Discount"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Service Filters",
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
                        solutionFilterOptions.forEach { (key, displayName) ->
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
                
                // Поле ввода для значения фильтра
                OutlinedTextField(
                    value = getFilterValue(filterState),
                    onValueChange = { newValue ->
                        onFilterChange(updateFilterValue(filterState, newValue))
                    },
                    label = { Text("Value") },
                    modifier = Modifier.weight(1f),
                    enabled = filterState.selectedFilter.isNotEmpty()
                )
                
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

private fun getFilterValue(filterState: SolutionFilterState): String {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.name
        "Description" -> filterState.description
        "Category" -> filterState.category
        "Price" -> filterState.price
        "Discount" -> filterState.discount
        else -> ""
    }
}

private fun updateFilterValue(filterState: SolutionFilterState, newValue: String): SolutionFilterState {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.copy(name = newValue)
        "Description" -> filterState.copy(description = newValue)
        "Category" -> filterState.copy(category = newValue)
        "Price" -> filterState.copy(price = newValue)
        "Discount" -> filterState.copy(discount = newValue)
        else -> filterState
    }
}
