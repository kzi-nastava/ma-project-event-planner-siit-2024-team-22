package com.example.eventplannerteam22.solutions.presentation.viewSolution

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolutionsViewModel @Inject constructor(
    private val repository: SolutionRepository
) : ViewModel() {

    var solutions by mutableStateOf<List<Solution>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreSolutions by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3

    init {
        loadSolutions()
    }

    fun loadSolutions() {
        if (isLoading || !hasMoreSolutions) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newSolutions = repository.getSolutions(limit, currentOffset)
                solutions = solutions + newSolutions
                currentOffset += limit

                hasMoreSolutions = newSolutions.size == limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}