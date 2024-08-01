package com.app.newsappsample.navigation

sealed class Screen(
    val route: String,
) {
    data object Splash : Screen("splash")

    data object NewsList : Screen("newsList")

    data object NewsDetail : Screen("newsDetail")
}
