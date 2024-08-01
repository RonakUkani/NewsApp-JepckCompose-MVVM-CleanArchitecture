package com.app.domain.usecase

import com.app.data.remote.model.NewsResponseDTO
import com.app.data.remote.repository.NewsRepository
import com.app.data.remote.result.NetworkResult
import com.app.domain.mapper.NewsMapper
import com.app.domain.model.News

class NewsUseCaseImpl(
    private val newsRepository: NewsRepository,
    private val newsMapper: NewsMapper
) : NewsUseCase {

    override suspend fun getEverythingFirst(
        query: String,
        from: String,
        sortBy: String,
        apiKey: String
    ): NetworkResult<News> {
        return newsRepository.getEverythingFirst(
            query = query,
            from = from,
            sortBy = sortBy,
            apiKey = apiKey
        ).handleNetWorkResult()
    }

    override suspend fun getEverythingSecond(domains: String, apiKey: String): NetworkResult<News> {
        return newsRepository.getEverythingSecond(
            domains = domains,
            apiKey = apiKey
        ).handleNetWorkResult()
    }

    override suspend fun getTopHeadline(sources: String, apiKey: String): NetworkResult<News> {
        return newsRepository.getTopHeadline(
            sources = sources,
            apiKey = apiKey
        ).handleNetWorkResult()
    }

    override suspend fun getBusinessTopHeadline(
        country: String,
        category: String,
        apiKey: String
    ): NetworkResult<News> {
        return newsRepository.getBusinessTopHeadline(
            country = country,
            category = category,
            apiKey = apiKey
        ).handleNetWorkResult()
    }

    // create common handleNetWorkResult to map success and fail data to avoid boilerplate code.
    private fun NetworkResult<NewsResponseDTO>.handleNetWorkResult(): NetworkResult<News> {
        return when (this) {
            is NetworkResult.Success -> {
                NetworkResult.Success(newsMapper.map(data))
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(message, cause)
            }
        }
    }

}