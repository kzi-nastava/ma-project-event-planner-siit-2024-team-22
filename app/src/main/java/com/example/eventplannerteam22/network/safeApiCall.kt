package com.example.eventplannerteam22.network

import retrofit2.HttpException
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException){
        when(e.code()){
            400 -> ApiResult.BadRequest(e.message())
            401 -> ApiResult.Unauthorized
            403 -> ApiResult.Forbidden
            404 -> ApiResult.NotFound
            in 500..599 -> ApiResult.ServerError(e.code(), e.message())
            else -> ApiResult.UnknownError(e.code(), e.message())
        }
    }
}