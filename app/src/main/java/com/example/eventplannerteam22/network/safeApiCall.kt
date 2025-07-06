package com.example.eventplannerteam22.network

import retrofit2.HttpException
import java.net.ConnectException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> ApiResult.BadRequest(e.message())
            401 -> ApiResult.Unauthorized(e.message())
            403 -> ApiResult.Forbidden(e.message())
            404 -> ApiResult.NotFound(e.message())
            409 -> ApiResult.Conflict(e.message())
            in 500..599 -> ApiResult.ServerError(e.code(), e.message())
            else -> ApiResult.UnknownError(e.code(), e.message())
        }
    } catch (e: ConnectException) {
        ApiResult.ConnectionError(e.message ?: "Failed to connect to the server")
    }
}