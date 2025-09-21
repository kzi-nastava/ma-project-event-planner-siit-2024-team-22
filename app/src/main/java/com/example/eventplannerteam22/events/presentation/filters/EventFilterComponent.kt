package com.example.eventplannerteam22.events.presentation.filters

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

data class EventFilterState(
    val name: String = "",
    val location: String = "",
    val eventType: String = "",
    val maxCapacity: String = "",
    val isPrivate: String = "",
    val selectedFilter: String = "",
    val isExpanded: Boolean = false,
    val isPrivateExpanded: Boolean = false
)

@Composable
fun EventFilterComponent(
    filterState: EventFilterState,
    onFilterChange: (EventFilterState) -> Unit,
    onClearFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val eventFilterOptions = listOf(
        "name" to "Name",
        "location" to "Location", 
        "eventType" to "Event Type",
        "maxCapacity" to "Max Capacity",
        "isPrivate" to "Private"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Event Filters",
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
                        eventFilterOptions.forEach { (key, displayName) ->
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

private fun getFilterValue(filterState: EventFilterState): String {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.name
        "Location" -> filterState.location
        "Event Type" -> filterState.eventType
        "Max Capacity" -> filterState.maxCapacity
        "Private" -> filterState.isPrivate
        else -> ""
    }
}

private fun updateFilterValue(filterState: EventFilterState, newValue: String): EventFilterState {
    return when (filterState.selectedFilter) {
        "Name" -> filterState.copy(name = newValue)
        "Location" -> filterState.copy(location = newValue)
        "Event Type" -> filterState.copy(eventType = newValue)
        "Max Capacity" -> filterState.copy(maxCapacity = newValue)
        "Private" -> filterState.copy(isPrivate = newValue)
        else -> filterState
    }
}
