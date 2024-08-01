package com.app.data.remote.api

import com.app.data.remote.model.NewsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET(ApiEndpoints.EVERYTHING)
    suspend fun getEverythingFirst(
        @Query(ApiParameters.Q) query: String,
        @Query(ApiParameters.FROM) from: String,
        @Query(ApiParameters.SORT_BY) sortBy: String,
        @Query(ApiParameters.API_KEY) apiKey: String
    ): Response<NewsResponseDTO>

    @GET(ApiEndpoints.EVERYTHING)
    suspend fun getEverythingSecond(
        @Query(ApiParameters.DOMAINS) domains: String,
        @Query(ApiParameters.API_KEY) apiKey: String
    ): Response<NewsResponseDTO>

    @GET(ApiEndpoints.TOP_HEADLINES)
    suspend fun getTopHeadline(
        @Query(ApiParameters.SOURCES) sources: String,
        @Query(ApiParameters.API_KEY) apiKey: String
    ): Response<NewsResponseDTO>

    @GET(ApiEndpoints.TOP_HEADLINES)
    suspend fun getBusinessTopHeadline(
        @Query(ApiParameters.COUNTRY) country: String,
        @Query(ApiParameters.CATEGORY) category: String,
        @Query(ApiParameters.API_KEY) apiKey: String
    ): Response<NewsResponseDTO>
}