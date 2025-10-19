package com.example.eventplannerteam22.admin.reports.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.userreport.presentation.UserReport
import com.example.eventplannerteam22.userreport.presentation.UserReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminReportModerationViewModel @Inject constructor(
    private val repository: UserReportRepository
) : ViewModel() {
    var reports by mutableStateOf<List<UserReport>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun loadPendingReports() {
        isLoading = true
        viewModelScope.launch {
            val result = repository.getPendingReports()
            result.onSuccess { reportList ->
                reports = reportList
                error = null
            }
            result.onFailure { exception ->
                error = exception.message ?: "Failed to load reports"
            }
            isLoading = false
        }
    }

    fun updateReportStatus(reportId: Int, status: String) {
        isLoading = true
        viewModelScope.launch {
            val result = repository.updateReportStatus(reportId, status)
            result.onSuccess { updatedReport ->
                reports = reports.map { report ->
                    if (report.id == reportId) updatedReport else report
                }
                error = null
            }
            result.onFailure { exception ->
                error = exception.message ?: "Failed to update report status"
            }
            isLoading = false
        }
    }

    fun clearError() {
        error = null
    }
}
