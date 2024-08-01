package com.app.newsappsample

import android.app.Application
import com.app.data.di.retrofitModule
import com.app.domain.di.useCaseModule
import com.app.newsappsample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(listOf(viewModelModule, useCaseModule, retrofitModule))
        }
    }
}
