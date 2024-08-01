package com.app.newsappsample.presentation.view.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.newsappsample.R
import com.app.newsappsample.navigation.Screen

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Splash() {
    SplashScreen(navController = rememberNavController())
}

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )

        // Go to the NewsList and clear splash screen from stack
        navController.navigate(Screen.NewsList.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_news_app_logo_foreground),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .fillMaxSize()
        )
    }
}
