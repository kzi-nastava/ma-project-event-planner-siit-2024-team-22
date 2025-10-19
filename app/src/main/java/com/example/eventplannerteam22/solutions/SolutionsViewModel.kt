package com.example.eventplannerteam22.solutions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.Solution
import com.example.eventplannerteam22.solutions.presentation.filters.SolutionFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionsViewModel @Inject constructor(
    private val repository: SolutionRepository,
    private val categoryRepository: com.example.eventplannerteam22.solutionCategory.data.SolutionCategoryRepository
) : ViewModel() {

    var solutions by mutableStateOf<List<Solution>>(emptyList())
        private set

    var filteredSolutions by mutableStateOf<List<Solution>>(emptyList())
        private set

    var isFiltered by mutableStateOf(false)
        private set

    var filterState by mutableStateOf(SolutionFilterState())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreSolutions by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3

    init {
        loadSolutions()
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getSolutionCategories(100, 0)
                updateFilterState(filterState.copy(categories = categories))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadSolutions() {
        if (isLoading || !hasMoreSolutions) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newSolutions = repository.getSolutions(limit, currentOffset)
                solutions = solutions + newSolutions
                filteredSolutions = solutions
                currentOffset += limit

                hasMoreSolutions = newSolutions.size == limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateFilterState(newFilterState: SolutionFilterState) {
        filterState = newFilterState
    }

    fun applyFilters() {
        viewModelScope.launch {
            isLoading = true
            
            try {
                val nameParam = filterState.name.takeIf { it.isNotEmpty() }
                val descParam = filterState.description.takeIf { it.isNotEmpty() }
                val priceParam = filterState.price.toDoubleOrNull()
                val discountParam = filterState.discount.toDoubleOrNull()
                
                println("Filter params: name=$nameParam, desc=$descParam, categoryId=${filterState.selectedCategory?.id}, price=$priceParam, discount=$discountParam")
                
                val result = repository.searchAndFilterSolutions(
                    name = nameParam,
                    description = descParam,
                    categoryId = filterState.selectedCategory?.id,
                    price = priceParam,
                    discount = discountParam
                )
                
                when (result) {
                    is com.example.eventplannerteam22.network.ApiResult.Success -> {
                        filteredSolutions = result.data
                        isFiltered = true
                    }
                    else -> {
                        // Если нет результатов или ошибка, показываем пустой список
                        filteredSolutions = emptyList()
                        isFiltered = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                filteredSolutions = solutions
                isFiltered = false
            } finally {
                isLoading = false
            }
        }
    }

    fun clearFilters() {
        filterState = SolutionFilterState()
        filteredSolutions = solutions
        isFiltered = false
    }
}