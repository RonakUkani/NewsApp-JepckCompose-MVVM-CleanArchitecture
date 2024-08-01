package com.app.newsappsample.utils

import com.app.domain.model.News
import com.app.domain.model.News.Article.Source

// is help to display dummy data or we can used for unit testing
fun getDummyNewsArticle() = News.Article(
    author = "Emily Bary, Tomi Kilgore",
    content = "Investors in Meta's stock shouldn't expect another double-digit, post-earnings move, or at least that what the options market is saying.\\r\\nWill the options market be right for a change?\\r\\nFollowing Met… [+939 chars]",
    description = "Meta reported its second-quarter earnings after Wednesday's close. MarketWatch will be breaking down the expectations and results.",
    publishedAt = "2024-07-31T20:08:49Z",
    source = Source(
        id = "the-wall-street-journal",
        name = "The Wall Street Journal",
    ),
    title = "Meta earnings: Stock rises as the Facebook parent gives upbeat outlook",
    url = "https://www.wsj.com/livecoverage/meta-earnings-results-ai-q2-facebook-revenue-instagram",
    urlToImage = "https://images.mktw.net/im-93074937/social",
    isFavorite = false
)
