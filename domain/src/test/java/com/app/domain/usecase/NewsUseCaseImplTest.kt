package com.app.domain.usecase

import com.app.data.remote.model.NewsResponseDTO
import com.app.data.remote.repository.NewsRepository
import com.app.data.remote.result.NetworkResult
import com.app.domain.mapper.NewsMapper
import com.app.domain.model.News
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsUseCaseImplTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var newsMapper: NewsMapper
    private lateinit var newsUseCase: NewsUseCaseImpl

    private val mockNewsResponseDTO = mockk<NewsResponseDTO>()
    private val mockNews = mockk<News>()

    @Before
    fun setUp() {
        newsRepository = mockk()
        newsMapper = mockk()
        newsUseCase = NewsUseCaseImpl(newsRepository, newsMapper)
    }

    @Test
    fun `getEverythingFirst returns mapped success result`() = runBlocking {
        coEvery { newsRepository.getEverythingFirst(any(), any(), any(), any()) } returns NetworkResult.Success(mockNewsResponseDTO)
        every { newsMapper.map(mockNewsResponseDTO) } returns mockNews

        val result = newsUseCase.getEverythingFirst("query", "from", "sortBy", "apiKey")

        assert(result is NetworkResult.Success)
        assertEquals(mockNews, (result as NetworkResult.Success).data)
        coVerify { newsRepository.getEverythingFirst("query", "from", "sortBy", "apiKey") }
        verify { newsMapper.map(mockNewsResponseDTO) }
    }

    @Test
    fun `getEverythingFirst returns mapped error result`() = runBlocking {
        val errorMessage = "Error message"
        val exception = Exception("Error")
        coEvery { newsRepository.getEverythingFirst(any(), any(), any(), any()) } returns NetworkResult.Error(errorMessage, exception)

        val result = newsUseCase.getEverythingFirst("query", "from", "sortBy", "apiKey")

        assert(result is NetworkResult.Error)
        assertEquals(errorMessage, (result as NetworkResult.Error).message)
        assertEquals(exception, result.cause)
        coVerify { newsRepository.getEverythingFirst("query", "from", "sortBy", "apiKey") }
    }

    @Test
    fun `getEverythingSecond returns mapped success result`() = runBlocking {
        coEvery { newsRepository.getEverythingSecond(any(), any()) } returns NetworkResult.Success(mockNewsResponseDTO)
        every { newsMapper.map(mockNewsResponseDTO) } returns mockNews

        val result = newsUseCase.getEverythingSecond("domains", "apiKey")

        assert(result is NetworkResult.Success)
        assertEquals(mockNews, (result as NetworkResult.Success).data)
        coVerify { newsRepository.getEverythingSecond("domains", "apiKey") }
        verify { newsMapper.map(mockNewsResponseDTO) }
    }

    @Test
    fun `getTopHeadline returns mapped success result`() = runBlocking {
        coEvery { newsRepository.getTopHeadline(any(), any()) } returns NetworkResult.Success(mockNewsResponseDTO)
        every { newsMapper.map(mockNewsResponseDTO) } returns mockNews

        val result = newsUseCase.getTopHeadline("sources", "apiKey")

        assert(result is NetworkResult.Success)
        assertEquals(mockNews, (result as NetworkResult.Success).data)
        coVerify { newsRepository.getTopHeadline("sources", "apiKey") }
        verify { newsMapper.map(mockNewsResponseDTO) }
    }

    @Test
    fun `getBusinessTopHeadline returns mapped success result`() = runBlocking {
        coEvery { newsRepository.getBusinessTopHeadline(any(), any(), any()) } returns NetworkResult.Success(mockNewsResponseDTO)
        every { newsMapper.map(mockNewsResponseDTO) } returns mockNews

        val result = newsUseCase.getBusinessTopHeadline("country", "category", "apiKey")

        assert(result is NetworkResult.Success)
        assertEquals(mockNews, (result as NetworkResult.Success).data)
        coVerify { newsRepository.getBusinessTopHeadline("country", "category", "apiKey") }
        verify { newsMapper.map(mockNewsResponseDTO) }
    }
}
