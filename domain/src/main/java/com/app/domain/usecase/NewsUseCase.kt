package com.app.domain.usecase

import com.app.data.remote.result.NetworkResult
import com.app.domain.model.News

interface NewsUseCase {

    suspend fun getEverythingFirst(
        query: String,
        from: String,
        sortBy: String,
        apiKey: String,
    ): NetworkResult<News>

    suspend fun getEverythingSecond(
        domains: String,
        apiKey: String,
    ): NetworkResult<News>

    suspend fun getTopHeadline(
        sources: String,
        apiKey: String,
    ): NetworkResult<News>

    suspend fun getBusinessTopHeadline(
        country: String,
        category: String,
        apiKey: String,
    ): NetworkResult<News>
}