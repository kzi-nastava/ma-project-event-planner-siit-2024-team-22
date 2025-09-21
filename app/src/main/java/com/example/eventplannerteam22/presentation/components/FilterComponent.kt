package com.example.eventplannerteam22.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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

data class FilterOption(
    val key: String,
    val displayName: String
)

data class FilterState(
    val selectedFilter: String = "",
    val searchValue: String = "",
    val isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterComponent(
    filterOptions: List<FilterOption>,
    onFilterChange: (String, String) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    var filterState by remember { mutableStateOf(FilterState()) }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Фильтры",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Выпадающее меню для выбора типа фильтра
                ExposedDropdownMenuBox(
                    expanded = filterState.isExpanded,
                    onExpandedChange = { 
                        filterState = filterState.copy(isExpanded = !filterState.isExpanded)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = filterState.selectedFilter,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Тип фильтра") },
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
                            filterState = filterState.copy(isExpanded = false)
                        }
                    ) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.displayName) },
                                onClick = {
                                    filterState = filterState.copy(
                                        selectedFilter = option.displayName,
                                        isExpanded = false
                                    )
                                }
                            )
                        }
                    }
                }
                
                // Поле ввода для значения фильтра
                OutlinedTextField(
                    value = filterState.searchValue,
                    onValueChange = { newValue ->
                        filterState = filterState.copy(searchValue = newValue)
                        val selectedOption = filterOptions.find { it.displayName == filterState.selectedFilter }
                        if (selectedOption != null) {
                            onFilterChange(selectedOption.key, newValue)
                        }
                    },
                    label = { Text("Значение") },
                    modifier = Modifier.weight(1f),
                    enabled = filterState.selectedFilter.isNotEmpty()
                )
                
                // Кнопка очистки
                IconButton(
                    onClick = {
                        filterState = FilterState()
                        onClearFilters()
                    }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Очистить фильтры"
                    )
                }
            }
        }
    }
}

@Composable
fun MultiFilterComponent(
    filters: List<FilterRow>,
    onFilterChange: (Int, String, String) -> Unit,
    onClearAllFilters: () -> Unit,
    onAddFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Фильтры",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row {
                    Button(
                        onClick = onAddFilter,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Добавить")
                    }
                    
                    Button(
                        onClick = onClearAllFilters
                    ) {
                        Text("Очистить все")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            filters.forEachIndexed { index, filterRow ->
                FilterRowComponent(
                    filterRow = filterRow,
                    onFilterChange = { key, value ->
                        onFilterChange(index, key, value)
                    },
                    onRemoveFilter = {
                        // Логика удаления фильтра
                    }
                )
                
                if (index < filters.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

data class FilterRow(
    val selectedFilter: String = "",
    val searchValue: String = "",
    val isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRowComponent(
    filterRow: FilterRow,
    onFilterChange: (String, String) -> Unit,
    onRemoveFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Выпадающее меню для выбора типа фильтра
        ExposedDropdownMenuBox(
            expanded = filterRow.isExpanded,
            onExpandedChange = { },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = filterRow.selectedFilter,
                onValueChange = { },
                readOnly = true,
                label = { Text("Тип фильтра") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = filterRow.isExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            
            ExposedDropdownMenu(
                expanded = filterRow.isExpanded,
                onDismissRequest = { }
            ) {
                // Здесь будут опции фильтров
            }
        }
        
        // Поле ввода для значения фильтра
        OutlinedTextField(
            value = filterRow.searchValue,
            onValueChange = { newValue ->
                onFilterChange(filterRow.selectedFilter, newValue)
            },
            label = { Text("Значение") },
            modifier = Modifier.weight(1f),
            enabled = filterRow.selectedFilter.isNotEmpty()
        )
        
        // Кнопка удаления
        IconButton(onClick = onRemoveFilter) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Удалить фильтр"
            )
        }
    }
}
