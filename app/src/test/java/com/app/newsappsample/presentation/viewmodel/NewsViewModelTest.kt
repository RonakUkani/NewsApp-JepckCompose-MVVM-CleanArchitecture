package com.app.newsappsample.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.app.data.remote.result.NetworkResult
import com.app.domain.model.News
import com.app.domain.usecase.NewsUseCase
import com.app.newsappsample.utils.getDummyNewsArticle
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val application: Application = mockk(relaxed = true)
    private val newsUseCase: NewsUseCase = mockk(relaxed = true)
    private lateinit var viewModel: NewsViewModel
    private val article = getDummyNewsArticle()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewsViewModel(newsUseCase, application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `setSelectedArticle updates selectedArticle LiveData`() {
        viewModel.setSelectedArticle(article)
        assertEquals(article, viewModel.selectedArticle.value)
    }

    @Test
    fun `getNewsDetail updates LiveData`() {
        viewModel.getNewsDetail()
        assertEquals(getDummyNewsArticle(), viewModel.selectedArticle.value)
    }

    @Test
    fun `getAllTopHeadlines sets uiState correctly on success`() = runTest {
        val articles = listOf(article)

        coEvery {
            newsUseCase.getEverythingFirst(
                any(),
                any(),
                any(),
                any()
            )
        } returns NetworkResult.Success(
            News(
                articles = mutableListOf(article),
                status = "status",
                message = "message",
                totalResults = 1
            )
        )
        coEvery { newsUseCase.getTopHeadline(any(), any()) } returns NetworkResult.Success(
            News(
                articles = mutableListOf(article),
                status = "status",
                message = "message",
                totalResults = 1
            )
        )
        coEvery {
            newsUseCase.getEverythingSecond(
                any(),
                any()
            )
        } returns NetworkResult.Success(
            News(
                articles = mutableListOf(article),
                status = "status",
                message = "message",
                totalResults = 1
            )
        )

        coEvery {
            newsUseCase.getBusinessTopHeadline(
                any(),
                any(),
                any()
            )
        } returns NetworkResult.Success(
            News(
                articles = mutableListOf(article),
                status = "status",
                message = "message",
                totalResults = 1
            )
        )

        viewModel.uiState.test {
            val viewModel = NewsViewModel(newsUseCase, application)
            viewModel.refreshData()

            awaitItem()

            assertEquals(false, viewModel.uiState.value.topHeadlinesLoading)
            assertEquals(false, viewModel.uiState.value.businessLoading)
            assertEquals(articles, viewModel.uiState.value.everythingFirstNews)
            assertEquals(articles, viewModel.uiState.value.topHeadlineNews)
            assertEquals(articles, viewModel.uiState.value.everythingSecondNews)
            assertEquals(articles, viewModel.uiState.value.businessHeadlineNews)
        }

    }

    @Test
    fun `getAllTopHeadlines sets uiState correctly on error`() = runTest {
        val errorMessage = "Network error"

        coEvery {
            newsUseCase.getEverythingFirst(
                any(),
                any(),
                any(),
                any()
            )
        } returns NetworkResult.Error(errorMessage)
        coEvery { newsUseCase.getTopHeadline(any(), any()) } returns NetworkResult.Error(
            errorMessage
        )
        coEvery { newsUseCase.getEverythingSecond(any(), any()) } returns NetworkResult.Error(
            errorMessage
        )
        coEvery {
            newsUseCase.getBusinessTopHeadline(
                any(),
                any(),
                any()
            )
        } returns NetworkResult.Error(errorMessage)

        viewModel.uiState.test {
            val viewModel = NewsViewModel(newsUseCase, application)
            viewModel.refreshData()

            awaitItem()

            assertEquals(emptyList<News.Article>(), viewModel.uiState.value.everythingFirstNews)
            assertEquals(emptyList<News.Article>(), viewModel.uiState.value.topHeadlineNews)
            assertEquals(emptyList<News.Article>(), viewModel.uiState.value.everythingSecondNews)
            assertEquals(emptyList<News.Article>(), viewModel.uiState.value.businessHeadlineNews)
        }
    }
}
