package com.app.data.remote.util

import com.app.data.remote.result.NetworkResult
import com.app.data.remote.result.handleException
import retrofit2.Response

internal fun <T> Response<T>.handleBaseResponse(): NetworkResult<T> {
    return try {
        if (this.isSuccessful) {
            body()?.let {
                NetworkResult.Success(it)
            } ?: NetworkResult.Error("No data available")
        } else {
            NetworkResult.Error("Error: ${code()} ${message()}")
        }
    } catch (e: Exception) {
        handleException(e)
    }
}
