package com.data.remote.util

import com.app.data.remote.result.NetworkResult
import com.app.data.remote.util.handleBaseResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class ResponseExtensionsTest {

    @Test
    fun `test handleBaseResponse with successful response`() {
        val mockResponse = mockk<Response<String>>()
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns "Success Data"

        val result = mockResponse.handleBaseResponse()

        assert(result is NetworkResult.Success)
        assertEquals("Success Data", (result as NetworkResult.Success).data)
    }

    @Test
    fun `test handleBaseResponse with successful response but no body`() {
        val mockResponse = mockk<Response<String>>()
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns null

        val result = mockResponse.handleBaseResponse()

        assert(result is NetworkResult.Error)
        assertEquals("No data available", (result as NetworkResult.Error).message)
    }

    @Test
    fun `test handleBaseResponse with error response`() {
        val mockResponse = mockk<Response<String>>()
        every { mockResponse.isSuccessful } returns false
        every { mockResponse.code() } returns 404
        every { mockResponse.message() } returns "Not Found"

        val result = mockResponse.handleBaseResponse()

        assert(result is NetworkResult.Error)
        assertEquals("Error: 404 Not Found", (result as NetworkResult.Error).message)
    }

    @Test
    fun `test handleBaseResponse with exception thrown`() {
        val mockResponse = mockk<Response<String>>()
        every { mockResponse.isSuccessful } throws RuntimeException("Unexpected error")

        val result = mockResponse.handleBaseResponse()

        assert(result is NetworkResult.Error)
        assertEquals("An unexpected error occurred", (result as NetworkResult.Error).message)
    }
}
