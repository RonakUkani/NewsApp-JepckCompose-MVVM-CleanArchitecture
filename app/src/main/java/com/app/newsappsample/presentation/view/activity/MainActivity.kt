package com.app.newsappsample.presentation.view.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.app.newsappsample.navigation.RegisterBaseNavigation
import com.app.newsappsample.presentation.ui.theme.NewsAppSampleTheme
import com.app.newsappsample.presentation.ui.theme.White

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // RegisterBaseNavigation with nav controller
        // open splash screen from RegisterBaseNavigation fun
        setContent {
            NewsAppSampleTheme {
                Surface(color = White, modifier = Modifier.fillMaxSize()) {
                    rememberNavController().RegisterBaseNavigation()
                }
            }
        }
    }
}
