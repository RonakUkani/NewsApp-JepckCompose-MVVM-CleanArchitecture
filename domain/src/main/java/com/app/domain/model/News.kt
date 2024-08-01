package com.app.domain.model

data class News(
    val articles: MutableList<Article>,
    val status: String,
    val message: String,
    val totalResults: Int,
) {
    data class Article(
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String,
        var isFavorite: Boolean
    ) {
        data class Source(
            val id: String,
            val name: String,
        )
    }
}