package com.example.eventplannerteam22.network

import android.content.Context
import android.util.Log
import android.widget.Toast

fun <T> apiResultHandler(
    onSuccess: () -> Unit,
    apiResult: ApiResult<T>,
    logTag: String,
    context: Context,
    badRequestErrorText: String? = "Bad request",
    unauthorizedErrorText: String? = "Unauthorized",
    forbiddenErrorText: String? = "Forbidden",
    notFoundErrorText: String? = "Not found",
    conflictErrorText: String? = "Conflict",
    serverErrorText: String? = "Server error",
    unknownErrorText: String? = "Unknown error",
    connectionErrorText: String? = "Failed to connect to the server. Check network connection or try again later"
) {
    when (apiResult) {
        is ApiResult.Success -> {
            Log.w(logTag, "Successful login: ${apiResult.data}")
            onSuccess()
        }

        is ApiResult.BadRequest -> {
            Log.w(logTag, "BadRequest: ${apiResult.message}")
            Toast.makeText(
                context,
                apiResult.message ?: badRequestErrorText,
                Toast.LENGTH_SHORT
            )
                .show()
        }

        is ApiResult.Unauthorized -> {
            Log.w(logTag, "Unauthorized: ${apiResult.message}")
            Toast.makeText(
                context,
                unauthorizedErrorText,
                Toast.LENGTH_SHORT
            ).show()
        }

        is ApiResult.Forbidden -> {
            Log.w(logTag, "Forbidden: ${apiResult.message}")
            Toast.makeText(
                context,
                forbiddenErrorText,
                Toast.LENGTH_SHORT
            ).show()
        }

        is ApiResult.NotFound -> {
            Log.w(logTag, "NotFound: ${apiResult.message}")
            Toast.makeText(
                context,
                notFoundErrorText,
                Toast.LENGTH_SHORT
            ).show()
        }

        is ApiResult.Conflict -> {
            Log.w(logTag, "Conflict: ${apiResult.message}")
            Toast.makeText(
                context,
                conflictErrorText,
                Toast.LENGTH_SHORT
            ).show()
        }

        is ApiResult.ServerError -> {
            Log.e(logTag, "Server error: ${apiResult.code}: ${apiResult.message}")
            Toast.makeText(
                context,
                "$serverErrorText (${apiResult.code})",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        is ApiResult.UnknownError -> {
            Log.e(logTag, "UnknownError ${apiResult.code}: ${apiResult.message}")
            Toast.makeText(
                context,
                "$unknownErrorText: ${apiResult.code}",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        is ApiResult.ConnectionError -> {
            Log.e(logTag, "ConnectionError: ${apiResult.message}")
            Toast.makeText(
                context,
                "$connectionErrorText",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}