package com.app.domain.mapper

import com.app.data.remote.model.NewsResponseDTO
import com.app.domain.model.News
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsMapperTest {

    private val newsMapper = NewsMapper()

    @Test
    fun `map should correctly map NewsResponseDTO to News`() {
        val dto = NewsResponseDTO(
            articles = mutableListOf(
                NewsResponseDTO.Article(
                    author = "Author",
                    content = "Content",
                    description = "Description",
                    publishedAt = "2024-08-01T12:34:56Z",
                    source = NewsResponseDTO.Article.Source(id = "source_id", name = "Source Name"),
                    title = "Title",
                    url = "http://example.com",
                    urlToImage = "http://example.com/image.jpg",
                )
            ),
            status = "ok",
            message = "Message",
            totalResults = 1
        )

        val expectedNews = News(
            articles = mutableListOf(
                News.Article(
                    author = "Author",
                    content = "Content",
                    description = "Description",
                    publishedAt = "2024-08-01T12:34:56Z",
                    source = News.Article.Source(id = "source_id", name = "Source Name"),
                    title = "Title",
                    url = "http://example.com",
                    urlToImage = "http://example.com/image.jpg",
                    isFavorite = false
                )
            ),
            status = "ok",
            message = "Message",
            totalResults = 1
        )

        val mappedNews = newsMapper.map(dto)

        assertEquals(expectedNews, mappedNews)
    }

    @Test
    fun `mapArticle should handle null values correctly`() {
        val dto = NewsResponseDTO.Article(
            author = null,
            content = null,
            description = null,
            publishedAt = null,
            source = null,
            title = null,
            url = null,
            urlToImage = null
        )

        val expectedArticle = News.Article(
            author = "",
            content = "",
            description = "",
            publishedAt = "",
            source = News.Article.Source(id = "", name = ""),
            title = "",
            url = "",
            urlToImage = "",
            isFavorite = false
        )

        val mappedArticle = newsMapper.map(
            NewsResponseDTO(
                articles = mutableListOf(dto)
            )
        )

        assertEquals(expectedArticle, mappedArticle.articles[0])
    }
}
