package com.app.domain.mapper

import com.app.data.remote.model.NewsResponseDTO
import com.app.domain.model.News

// create mapper class, map API response data to presentation model
class NewsMapper {

    fun map(dto: NewsResponseDTO): News {
        return News(
            articles = dto.articles.map { mapArticle(it) }.toMutableList(),
            status = dto.status ?: "",
            message = dto.message,
            totalResults = dto.totalResults ?: 0
        )
    }

    private fun mapArticle(dto: NewsResponseDTO.Article): News.Article {
        return News.Article(
            author = dto.author ?: "",
            content = dto.content ?: "",
            description = dto.description ?: "",
            publishedAt = dto.publishedAt ?: "",
            source = dto.source?.let { mapSource(it) } ?: News.Article.Source("", ""),
            title = dto.title ?: "",
            url = dto.url ?: "",
            urlToImage = dto.urlToImage ?: "",
            isFavorite = false
        )
    }

    private fun mapSource(dto: NewsResponseDTO.Article.Source): News.Article.Source {
        return News.Article.Source(
            id = dto.id  ?: "",
            name = dto.name  ?: ""
        )
    }
}