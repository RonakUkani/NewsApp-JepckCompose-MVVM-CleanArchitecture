package com.app.newsappsample.presentation.view.newsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.app.domain.model.News
import com.app.newsappsample.R
import com.app.newsappsample.presentation.ui.theme.NewsAppSampleTheme
import com.app.newsappsample.presentation.ui.theme.TopBarColor
import com.app.newsappsample.presentation.ui.theme.White
import com.app.newsappsample.presentation.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsDetailPreview() {
    NewsDetailScreen(
        navController = rememberNavController(),
        newsViewModel = koinViewModel(),
        id = 1
    )
}

@Composable
fun NewsDetailScreen(
    navController: NavHostController,
    newsViewModel: NewsViewModel,
    id: Int?
) {
    // When id is not 0 it means user coming from deeplink
    // then we bind detail dummy data from viewmodel.
    if (id != 0) {
        newsViewModel.getNewsDetail()
    }

    // it is getting selectedArticle selectedArticle which we set on new list item click
    // for deeplink newsViewModel.getNewsDetail() is also post some data to selectedArticle
    val article by newsViewModel.selectedArticle.observeAsState()
    val isFavorite = remember { mutableStateOf(article?.isFavorite) }

    NewsAppSampleTheme {
        Surface(color = White, modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopBar(article, navController)
                },
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        item {
                            Column {
                                Image(
                                    painter = rememberImagePainter(
                                        data = article?.urlToImage,
                                        builder = {
                                            placeholder(R.drawable.dummy_image)
                                            error(R.drawable.dummy_image)
                                        }),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Column(modifier = Modifier.padding(16.dp)) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            painterResource(
                                                id =
                                                if (isFavorite.value == true) R.drawable.ic_star_fill
                                                else R.drawable.ic_star_outline
                                            ),
                                            contentDescription = "Star Icon",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clickable {
                                                    isFavorite.value = !isFavorite.value!!
                                                    article?.isFavorite = !article?.isFavorite!!
                                                }
                                        )
                                        Text(
                                            modifier = Modifier.padding(start = 5.dp),
                                            text = article?.author ?: "",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = article?.title ?: "",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = (article?.description ?: ""),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = (article?.content ?: ""),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
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
fun TopBar(article: News.Article?, navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = TopBarColor
        ),
        title = {
            Text(
                text = "${article?.title}",
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                // going back on back arrow click
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}
