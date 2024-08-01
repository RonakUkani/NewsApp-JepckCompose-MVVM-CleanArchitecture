package com.data.remote.repository

import com.app.data.remote.api.NewsService
import com.app.data.remote.model.NewsResponseDTO
import com.app.data.remote.repository.NewsRepository
import com.app.data.remote.result.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRepositoryTest {

    private lateinit var newsRepository: NewsRepository
    private var newsService: NewsService = mockk(relaxed = true)

    @Before
    fun setUp() {
        newsRepository = NewsRepository(newsService)
    }

    @Test
    fun `getEverythingFirst returns success when API call is successful`() = runBlocking {
        val query = "test"
        val from = "2024-08-01"
        val sortBy = "publishedAt"
        val apiKey = "api_key"
        val newsResponseDTO = NewsResponseDTO(mutableListOf(), "ok", "success")
        val response = Response.success(newsResponseDTO)

        coEvery {
            newsService.getEverythingFirst(query, from, sortBy, apiKey)
        } returns response

        val result = newsRepository.getEverythingFirst(query, from, sortBy, apiKey)

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data == newsResponseDTO)
    }

    @Test
    fun `getEverythingFirst returns error when API call fails`() = runBlocking {
        val query = "test"
        val from = "2024-08-01"
        val sortBy = "publishedAt"
        val apiKey = "api_key"
        val errorMessage = "An unexpected error occurred"

        coEvery {
            newsService.getEverythingFirst(
                query,
                from,
                sortBy,
                apiKey
            )
        } throws RuntimeException(errorMessage)

        val result = newsRepository.getEverythingFirst(query, from, sortBy, apiKey)

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).message == errorMessage)
    }

    @Test
    fun `getTopHeadline returns success when API call is successful`() = runBlocking {
        val sources = "sources"
        val apiKey = "api_key"
        val newsResponseDTO = NewsResponseDTO(mutableListOf(), "ok", "success")
        val response = Response.success(newsResponseDTO)

        coEvery {
            newsService.getTopHeadline(sources, apiKey)
        } returns response

        val result = newsRepository.getTopHeadline(sources, apiKey)

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data == newsResponseDTO)
    }

    @Test
    fun `getTopHeadline returns error when API call fails`() = runBlocking {
        val sources = "sources"
        val apiKey = "api_key"
        val errorMessage = "An unexpected error occurred"

        coEvery {
            newsService.getTopHeadline(
                sources,
                apiKey
            )
        } throws RuntimeException(errorMessage)

        val result = newsRepository.getTopHeadline(sources, apiKey)

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).message == errorMessage)
    }

    @Test
    fun `getEverythingSecond returns success when API call is successful`() = runBlocking {
        val domains = "domains"
        val apiKey = "api_key"
        val newsResponseDTO = NewsResponseDTO(mutableListOf(), "ok", "success")
        val response = Response.success(newsResponseDTO)

        coEvery {
            newsService.getEverythingSecond(domains, apiKey)
        } returns response

        val result = newsRepository.getEverythingSecond(domains, apiKey)

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data == newsResponseDTO)
    }

    @Test
    fun `getEverythingSecond returns error when API call fails`() = runBlocking {
        val domains = "domains"
        val apiKey = "api_key"
        val errorMessage = "An unexpected error occurred"

        coEvery {
            newsService.getEverythingSecond(
                domains,
                apiKey
            )
        } throws RuntimeException(errorMessage)

        val result = newsRepository.getEverythingSecond(domains, apiKey)

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).message == errorMessage)
    }

    @Test
    fun `getBusinessTopHeadline returns success when API call is successful`() = runBlocking {
        val country = "us"
        val category = "business"
        val apiKey = "api_key"
        val newsResponseDTO = NewsResponseDTO(mutableListOf(), "ok", "success")
        val response = Response.success(newsResponseDTO)

        coEvery {
            newsService.getBusinessTopHeadline(country, category, apiKey)
        } returns response

        val result = newsRepository.getBusinessTopHeadline(country, category, apiKey)

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data == newsResponseDTO)
    }

    @Test
    fun `getBusinessTopHeadline returns error when API call fails`() = runBlocking {
        val country = "us"
        val category = "business"
        val apiKey = "api_key"
        val errorMessage = "An unexpected error occurred"

        coEvery {
            newsService.getBusinessTopHeadline(country, category, apiKey)
        } throws RuntimeException(errorMessage)

        val result = newsRepository.getBusinessTopHeadline(country, category, apiKey)

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).message == errorMessage)
    }
}
