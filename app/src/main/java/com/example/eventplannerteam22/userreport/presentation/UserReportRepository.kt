package com.example.eventplannerteam22.userreport.presentation

import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class UserReportRepository @Inject constructor(
    private val api: UserReportApi
) {
    suspend fun getUserIdByEmail(email: String): Result<Int> = try {
        val user = api.getUserByEmail(email)
        Result.success(user.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun submitReport(reporterId: Int, reportedUserId: Int, text: String) {
        api.submitReport(CreateUserReportRequest(reporterId, reportedUserId, text))
    }
}
