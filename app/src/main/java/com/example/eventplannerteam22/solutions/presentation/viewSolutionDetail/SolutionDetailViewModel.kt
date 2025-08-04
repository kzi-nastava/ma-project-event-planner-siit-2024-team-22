package com.example.eventplannerteam22.solutions.presentation.viewSolutionDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionDetailViewModel @Inject constructor(
    private val repository: SolutionRepository
) : ViewModel() {
    private val _solution = MutableStateFlow<Solution?>(null)
    val solution: StateFlow<Solution?> = _solution

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadSolution(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _solution.value = repository.getSolutionById(id)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
}