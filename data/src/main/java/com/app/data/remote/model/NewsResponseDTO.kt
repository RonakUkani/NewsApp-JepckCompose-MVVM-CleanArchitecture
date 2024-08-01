package com.app.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class NewsResponseDTO (
    @SerializedName("articles")
    val articles: MutableList<Article> = mutableListOf(),
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("message")
    val message: String = "",
    @SerializedName("totalResults")
    val totalResults: Int? = 0,
) : Parcelable {
    @Parcelize
    data class Article(
        @SerializedName("author")
        val author: String? = "",
        @SerializedName("content")
        val content: String? = "",
        @SerializedName("description")
        val description: String? = "",
        @SerializedName("publishedAt")
        val publishedAt: String? = "",
        @SerializedName("source")
        val source: Source? = Source(),
        @SerializedName("title")
        val title: String? = "",
        @SerializedName("url")
        val url: String? = "",
        @SerializedName("urlToImage")
        val urlToImage: String? = "",
    ) : Parcelable {
        @Parcelize
        data class Source(
            @SerializedName("id")
            val id: String? = "",
            @SerializedName("name")
            val name: String? = "",
        ) : Parcelable
    }
}