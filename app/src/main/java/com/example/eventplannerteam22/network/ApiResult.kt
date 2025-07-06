package com.example.eventplannerteam22.network

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class BadRequest(val message: String? = null) : ApiResult<Nothing>()
    data class Unauthorized(val message: String? = null) : ApiResult<Nothing>()
    data class Forbidden(val message: String? = null) : ApiResult<Nothing>()
    data class NotFound(val message: String? = null) : ApiResult<Nothing>()
    data class Conflict(val message: String? = null) : ApiResult<Nothing>()
    data class ServerError(val code: Int, val message: String? = null) : ApiResult<Nothing>()
    data class UnknownError(val code: Int, val message: String? = null) : ApiResult<Nothing>()
    data class ConnectionError(val message: String? = null) : ApiResult<Nothing>()
}