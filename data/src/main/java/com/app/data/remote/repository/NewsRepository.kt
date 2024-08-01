package com.app.data.remote.repository

import com.app.data.remote.api.NewsService
import com.app.data.remote.model.NewsResponseDTO
import com.app.data.remote.result.NetworkResult
import com.app.data.remote.result.handleException
import com.app.data.remote.util.handleBaseResponse

class NewsRepository(private val newsService: NewsService) {

    // handleBaseResponse from common fun
    // handleException from common fun
    suspend fun getEverythingFirst(
        query: String,
        from: String,
        sortBy: String,
        apiKey: String,
    ): NetworkResult<NewsResponseDTO> {
        return try {
            newsService.getEverythingFirst(
                query = query,
                from = from,
                sortBy = sortBy,
                apiKey = apiKey
            ).handleBaseResponse()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun getEverythingSecond(
        domains: String,
        apiKey: String,
    ): NetworkResult<NewsResponseDTO> {
        return try {
            newsService.getEverythingSecond(
                domains = domains,
                apiKey = apiKey
            ).handleBaseResponse()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun getTopHeadline(
        sources: String,
        apiKey: String,
    ): NetworkResult<NewsResponseDTO> {
        return try {
            newsService.getTopHeadline(
                sources = sources,
                apiKey = apiKey
            ).handleBaseResponse()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun getBusinessTopHeadline(
        country: String,
        category: String,
        apiKey: String,
    ): NetworkResult<NewsResponseDTO> {
        return try {
            val result = newsService.getBusinessTopHeadline(
                country = country,
                category = category,
                apiKey = apiKey
            )
            if (result.isSuccessful) {
                result.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error("No data available")
            } else {
                NetworkResult.Error("Error: ${result.code()} ${result.message()}")
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
}