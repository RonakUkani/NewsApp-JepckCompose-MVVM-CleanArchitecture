package com.data.remote.result
import com.app.data.remote.result.NetworkResult
import com.app.data.remote.result.handleException
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkErrorHandlerTest {

    @Test
    fun `test handleException with HttpException`() {
        val response = Response.error<Any>(400, ResponseBody.create(null, "Bad Request"))
        val exception = HttpException(response)

        val result = handleException<Exception>(exception)

        assert(result is NetworkResult.Error)
        assertEquals("HTTP 400 Response.error()", (result as NetworkResult.Error).message)
        assertEquals(exception, result.cause)
    }

    @Test
    fun `test handleException with IOException`() {
        val exception = IOException("No internet connection")

        val result = handleException<Exception>(exception)

        assert(result is NetworkResult.Error)
        assertEquals(
            "Network error occurred. Please check your connection.",
            (result as NetworkResult.Error).message
        )
        assertEquals(exception, result.cause)
    }

    @Test
    fun `test handleException with unexpected exception`() {
        val exception = RuntimeException("Unexpected error")

        val result = handleException<Exception>(exception)

        assert(result is NetworkResult.Error)
        assertEquals("An unexpected error occurred", (result as NetworkResult.Error).message)
        assertEquals(exception, result.cause)
    }
}