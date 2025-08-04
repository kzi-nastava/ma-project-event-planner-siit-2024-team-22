package com.example.eventplannerteam22.solutionCategory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutionCategory.data.SolutionCategoryRepository
import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionCategoryViewModel @Inject constructor(
    private val repository: SolutionCategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SolutionCategoryState())
    val state: StateFlow<SolutionCategoryState> = _state.asStateFlow()

    init {
        loadSolutionCategories()
    }

    private fun loadSolutionCategories(limit: Int = 10, offset: Int = 0) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val categories = repository.getSolutionCategories(limit, offset)
                _state.update {
                    it.copy(
                        categories = categories,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load categories"
                    )
                }
            }
        }
    }

    fun addSolutionCategory(category: SolutionCategory) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.addSolutionCategory(category)
                loadSolutionCategories() // Refresh the list
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to add category"
                    )
                }
            }
        }
    }

    fun updateSolutionCategory(category: SolutionCategory) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.updateSolutionCategory(category)
                loadSolutionCategories() // Refresh the list
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to update category"
                    )
                }
            }
        }
    }

    fun retry() {
        loadSolutionCategories()
    }
}

data class SolutionCategoryState(
    val categories: List<SolutionCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)