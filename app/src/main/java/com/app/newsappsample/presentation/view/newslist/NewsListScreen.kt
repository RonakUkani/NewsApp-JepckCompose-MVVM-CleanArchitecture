package com.app.newsappsample.presentation.view.newslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.app.domain.model.News
import com.app.newsappsample.R
import com.app.newsappsample.navigation.Screen
import com.app.newsappsample.presentation.ui.theme.NewsAppSampleTheme
import com.app.newsappsample.presentation.ui.theme.TopBarColor
import com.app.newsappsample.presentation.ui.theme.White
import com.app.newsappsample.presentation.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsListPreview() {
    NewsListScreen(rememberNavController(), koinViewModel())
}

@Composable
fun NewsListScreen(navController: NavHostController, newsViewModel: NewsViewModel) {
    val uiState by newsViewModel.uiState.collectAsState()

    NewsList(uiState,
        // newsItemClick
        newsItemClick = {
            // setSelectedArticle to viewmodel
            newsViewModel.setSelectedArticle(it)

            // navigate to NewsDetail using navController
            navController.navigate(Screen.NewsDetail.route) {
                launchSingleTop = true
                restoreState = true
            }
        },
        // TopBar refresh button click
        refreshDataClick = {
            newsViewModel.refreshData()
        }
    )
}

@Composable
fun NewsList(
    uiState: NewsViewModel.NewsUiState,
    newsItemClick: (News.Article) -> Unit,
    refreshDataClick: () -> Unit
) {
    NewsAppSampleTheme {
        Surface(color = White, modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = { TopBar(refreshDataClick) },
                content = {

                    // Display Error view or news list view from both state
                    // if hideTopHeadlinesViews and hideBusinessViews is true then only error show in screen
                    if (uiState.hideTopHeadlinesViews && uiState.hideBusinessViews) {
                        ErrorUI(uiState)
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            item {
                                BindNewsData(uiState, newsItemClick)
                            }
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(refreshDataClick: () -> Unit) {
    TopAppBar(
        actions = {
            IconButton(onClick = { refreshDataClick.invoke() }) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = TopBarColor
        ),
        title = { Text(text = "News APP") })
}

@Composable
fun BindNewsData(uiState: NewsViewModel.NewsUiState, newsItemClick: (News.Article) -> Unit) {
    // when hideTopHeadlinesViews is not false the TopHeadLine show
    if (!uiState.hideTopHeadlinesViews) {
        TopHeadLine(uiState, newsItemClick)
    }

    // when hideBusinessViews is not false the BusinessLeadLine show
    if (!uiState.hideBusinessViews) {
        BusinessLeadLine(uiState, newsItemClick)
    }
}

@Composable
fun TopHeadLine(uiState: NewsViewModel.NewsUiState, newsItemClick: (News.Article) -> Unit) {
    val topHeadLineNews = listOf(
        uiState.everythingFirstNews,
        uiState.topHeadlineNews,
        uiState.everythingSecondNews
    )

    // Header Top Headline
    Text(
        text = "Top Headline",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
    )

    // When API calling then ProgressIndicator show and after getting response NewsColumn Data show
    if (uiState.topHeadlinesLoading) {
        ProgressIndicator()
    } else {
        NewsColumn(newsSections = topHeadLineNews, newsItemClick = newsItemClick)
    }
}

@Composable
fun BusinessLeadLine(uiState: NewsViewModel.NewsUiState, newsItemClick: (News.Article) -> Unit) {
    val businessList = listOf(uiState.businessHeadlineNews)

    // Header Business
    Text(
        text = "Business",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp, bottom = 8.dp, top = 10.dp)
    )

    // When API calling then businessLoading show and after getting response NewsColumn Data show
    if (uiState.businessLoading) {
        ProgressIndicator()
    } else {
        NewsColumn(newsSections = businessList, newsItemClick = newsItemClick)
    }
}

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ErrorUI(uiState: NewsViewModel.NewsUiState) {
    uiState.error?.let { errorMessage ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Error: $errorMessage",
                textAlign = TextAlign.Center,
                color = Color.Red,
            )
        }
    }
}

@Composable
fun NewsColumn(newsSections: List<List<News.Article>>, newsItemClick: (News.Article) -> Unit) {
    Column {
        newsSections.forEach { articles ->
            SectionRow(articles = articles, newsItemClick = newsItemClick)
        }
    }
}

@Composable
fun SectionRow(articles: List<News.Article>, newsItemClick: (News.Article) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        LazyRow {
            items(articles) { article ->
                NewsCard(newsArticle = article, newsItemClick = newsItemClick)
            }
        }
    }
}

@Composable
fun NewsCard(newsArticle: News.Article, newsItemClick: (News.Article) -> Unit) {
    val isFavorite = remember { mutableStateOf(newsArticle.isFavorite) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(150.dp)
            .padding(start = 10.dp)
            .clickable {
                newsItemClick(newsArticle)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = White,
        )
    ) {
        Column {
            Image(
                painter = rememberImagePainter(newsArticle.urlToImage,
                    builder = {
                        placeholder(R.drawable.dummy_image)
                        error(R.drawable.dummy_image)
                    }),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
            ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = newsArticle.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
                Icon(
                    painterResource(
                        id =
                        if (isFavorite.value) R.drawable.ic_star_fill
                        else R.drawable.ic_star_outline
                    ),
                    contentDescription = "Star Icon",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(0.2f)
                        .clickable {
                            isFavorite.value = !isFavorite.value
                            newsArticle.isFavorite = !newsArticle.isFavorite
                        }
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                text = newsArticle.author,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1
            )
        }
    }
}