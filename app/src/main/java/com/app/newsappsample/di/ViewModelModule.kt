package com.app.newsappsample.di

import android.app.Application
import com.app.newsappsample.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(), get<Application>())
    }
}
