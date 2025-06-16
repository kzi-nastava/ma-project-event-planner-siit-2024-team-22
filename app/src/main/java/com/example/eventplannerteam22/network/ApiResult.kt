package com.example.eventplannerteam22.network

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class BadRequest(val message: String? = null) : ApiResult<Nothing>()
    object Unauthorized : ApiResult<Nothing>()
    object Forbidden : ApiResult<Nothing>()
    object NotFound : ApiResult<Nothing>()
    data class ServerError(val code: Int, val message: String? = null) : ApiResult<Nothing>()
    data class UnknownError(val code: Int, val message: String? = null): ApiResult<Nothing>()
}