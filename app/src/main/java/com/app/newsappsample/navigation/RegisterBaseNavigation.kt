package com.app.newsappsample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.app.newsappsample.presentation.view.newsdetail.NewsDetailScreen
import com.app.newsappsample.presentation.view.newslist.NewsListScreen
import com.app.newsappsample.presentation.view.splash.SplashScreen
import com.app.newsappsample.presentation.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavHostController.RegisterBaseNavigation() {
    // Here Define shared NewsViewModel, so it will sharing inside NewsListScreen and NewsDetailScreen
    val newsViewModel: NewsViewModel = koinViewModel()

    // By Default pass Splash route so it will open first
    NavHost(this, startDestination = Screen.Splash.route) {

        // Open SplashScreen from here
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = this@RegisterBaseNavigation)
        }

        // Open NewsListScreen from here
        composable(route = Screen.NewsList.route) {
            NewsListScreen(navController = this@RegisterBaseNavigation, newsViewModel)
        }

        // NewsDetailScreen from here
        // deepLinks : manage deepLinks url
        // arguments : pass id which get from deeplink otherwise by default is 0
        composable(
            route = Screen.NewsDetail.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://news/detail/{id}"
                }
            ),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                }),
        ) { backStackEntry ->
            val id: Int? = backStackEntry.arguments?.getInt("id")
            NewsDetailScreen(navController = this@RegisterBaseNavigation, newsViewModel, id)
        }
    }
}
