package com.app.data.remote.result

import okio.IOException
import retrofit2.HttpException

fun <T> handleException(exception: Exception): NetworkResult<T> {
    return when (exception) {
        is HttpException -> {
            val message = exception.message ?: "HTTP error occurred"
            NetworkResult.Error(message, exception)
        }

        is IOException -> {
            NetworkResult.Error("Network error occurred. Please check your connection.", exception)
        }

        else -> {
            NetworkResult.Error("An unexpected error occurred", exception)
        }
    }
}