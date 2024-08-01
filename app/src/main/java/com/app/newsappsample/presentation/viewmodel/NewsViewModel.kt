package com.app.newsappsample.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.remote.result.NetworkResult
import com.app.domain.model.News
import com.app.domain.usecase.NewsUseCase
import com.app.newsappsample.utils.Constants
import com.app.newsappsample.utils.getDummyNewsArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsUseCase: NewsUseCase,
    private val application: Application,
) : ViewModel() {

    private val _selectedArticle = MutableLiveData<News.Article>()
    val selectedArticle: LiveData<News.Article> = _selectedArticle

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        refreshData()
    }

    fun refreshData() {
        getAllTopHeadlines()
    }

    fun setSelectedArticle(article: News.Article) {
        _selectedArticle.value = article
    }

    // when user coming from deeplink, here get dummyNews Article
    // because there is no API for get News Article from id
    fun getNewsDetail() {
        _selectedArticle.value = getDummyNewsArticle()
    }

    private fun getAllTopHeadlines(
        query: String = Constants.QUERY,
        from: String = Constants.FROM,
        sortBy: String = Constants.SORT_BY,
        apiKey: String = application.applicationContext.getString(com.app.data.R.string.apiKey),
        sources: String = Constants.SOURCE,
        domains: String = Constants.DOMAINS,
        country: String = Constants.COUNTRY,
        category: String = Constants.CATEGORY,
    ) {
        _uiState.update { it.copy(topHeadlinesLoading = true, businessLoading = true) }
        // callTopHeadlineAPI to get first 3 row data together
        callTopHeadlineAPI(query, from, sortBy, sources, domains, apiKey)

        // callBusinessAPI to get last row data
        callBusinessAPI(country, category, apiKey)
    }

    private fun callBusinessAPI(country: String, category: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val getBusinessTopHeadlineDeferred = newsUseCase.getBusinessTopHeadline(
                country = country,
                category = category,
                apiKey = apiKey
            )) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            businessLoading = false,
                            businessHeadlineNews = getBusinessTopHeadlineDeferred.data.articles,
                            hideBusinessViews = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            businessLoading = false,
                            error = getBusinessTopHeadlineDeferred.message,
                            hideBusinessViews = true
                        )
                    }
                }
            }
        }
    }

    private fun callTopHeadlineAPI(
        query: String,
        from: String,
        sortBy: String,
        sources: String,
        domains: String,
        apiKey: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // call first 3 api one by one using async await and get all the data in one short
            val everythingFirstNews = mutableListOf<News.Article>()
            val topHeadlineNews = mutableListOf<News.Article>()
            val everythingSecondNews = mutableListOf<News.Article>()
            val errorList = mutableListOf<String>()

            // call getEverythingFirst API for bind first row of top headline
            when (val everythingFirstResult = async {
                newsUseCase.getEverythingFirst(
                    query = query,
                    from = from,
                    sortBy = sortBy,
                    apiKey = apiKey
                )
            }.await()) {
                is NetworkResult.Success -> {
                    everythingFirstNews.addAll(everythingFirstResult.data.articles)
                }

                is NetworkResult.Error -> {
                    errorList.add(everythingFirstResult.message)
                }
            }

            // call getEverythingFirst API for bind second row of top headline
            when (val topHeadlineResult = async {
                newsUseCase.getTopHeadline(
                    sources = sources,
                    apiKey = apiKey,
                )
            }.await()) {
                is NetworkResult.Success -> {
                    topHeadlineNews.addAll(topHeadlineResult.data.articles)
                }

                is NetworkResult.Error -> {
                    errorList.add(topHeadlineResult.message)
                }
            }

            // call getEverythingFirst API for bind third row of top headline
            when (val everythingSecondResult = async {
                newsUseCase.getEverythingSecond(
                    domains = domains,
                    apiKey = apiKey
                )
            }.await()) {
                is NetworkResult.Success -> {
                    everythingSecondNews.addAll(everythingSecondResult.data.articles)
                }

                is NetworkResult.Error -> {
                    errorList.add(everythingSecondResult.message)
                }
            }

            // Here is all three list is empty means error of no-data
            // so, passed as error state to show error view in UI
            // otherwise in else pass store display state to bind data in list
            // inside else pass it post 3 list data which get from server
            if (everythingFirstNews.isEmpty() && topHeadlineNews.isEmpty() && everythingSecondNews.isEmpty()) {
                _uiState.update {
                    it.copy(
                        topHeadlinesLoading = false,
                        error = if (errorList.isNotEmpty()) errorList.last() else "No data available" ,
                        hideTopHeadlinesViews = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        topHeadlinesLoading = false,
                        everythingFirstNews = everythingFirstNews,
                        topHeadlineNews = topHeadlineNews,
                        everythingSecondNews = everythingSecondNews,
                        hideTopHeadlinesViews = false
                    )
                }
            }
        }
    }

    // create single NewsUiState to manage list suI
    // topHeadlinesLoading -> manage loader for topHeadlines row (1st row)
    // businessLoading -> manage loader for business row (1st row)
    // error -> store error message if any error.
    // hideTopHeadlinesViews -> is manage need to Top Headline whole UI (it hide if no data found foe all first three row)
    // hideBusinessViews -> is manage need to show Business Whole UI (it hide if no data found for business list)
    // all the List store news data
    data class NewsUiState(
        val topHeadlinesLoading: Boolean = false,
        val businessLoading: Boolean = false,
        val everythingFirstNews: List<News.Article> = emptyList(),
        val topHeadlineNews: List<News.Article> = emptyList(),
        val everythingSecondNews: List<News.Article> = emptyList(),
        val businessHeadlineNews: List<News.Article> = emptyList(),
        val error: String? = null,
        val hideTopHeadlinesViews: Boolean = false,
        val hideBusinessViews: Boolean = false,
    )
}