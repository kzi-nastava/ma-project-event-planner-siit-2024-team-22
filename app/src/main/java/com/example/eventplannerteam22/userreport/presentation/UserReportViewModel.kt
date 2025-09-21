package com.example.eventplannerteam22.userreport.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReportViewModel @Inject constructor(
    private val repository: UserReportRepository
) : ViewModel() {

    fun submitReport(
        reporterId: Int,
        reportedUserId: Int,
        text: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("UserReportViewModel", "POST /reports with reporterId=$reporterId, reportedUserId=$reportedUserId, text=$text")
                repository.submitReport(reporterId, reportedUserId, text)
                onSuccess()
            } catch (e: Exception) {
                Log.e("UserReportViewModel", "Error submitting report: ${e.message}", e)
                onError(e.message ?: "Failed to submit report")
            }
        }
    }

    fun submitReportByEmail(
        reporterId: Int,
        reportedUserEmail: String,
        text: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("UserReportViewModel", "GET /profiles/email?email=$reportedUserEmail")
                val userIdResult = repository.getUserIdByEmail(reportedUserEmail)
                if (userIdResult.isSuccess) {
                    val reportedUserId = userIdResult.getOrNull()!!
                    Log.d("UserReportViewModel", "POST /reports with reporterId=$reporterId, reportedUserId=$reportedUserId, text=$text")
                    repository.submitReport(reporterId, reportedUserId, text)
                    onSuccess()
                } else {
                    Log.e("UserReportViewModel", "User with email $reportedUserEmail not found")
                    onError("User with this email not found")
                }
            } catch (e: Exception) {
                Log.e("UserReportViewModel", "Error submitting report: ${e.message}", e)
                onError(e.message ?: "Failed to submit report")
            }
        }
    }
}
