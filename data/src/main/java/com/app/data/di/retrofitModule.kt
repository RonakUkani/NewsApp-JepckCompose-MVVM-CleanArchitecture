package com.app.data.di

import android.content.Context
import com.app.data.R
import com.app.data.remote.api.NewsService
import com.app.data.remote.repository.NewsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setVersion(1.0).create()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(httpLoggingInterceptor)
            build()
        }
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(context.getString(R.string.apiUrl))
            addConverterFactory(GsonConverterFactory.create(factory))
            client(client)
            build()
        }
    }

    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    fun provideNewsRepository(newsService: NewsService): NewsRepository {
        return NewsRepository(newsService)
    }

    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get(), get()) }
    single { provideNewsRepository(get()) }
    single { provideNewsService(get()) }
}