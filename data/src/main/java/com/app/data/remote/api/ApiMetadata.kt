package com.app.data.remote.api

object ApiEndpoints {
    private const val VERSION = "v2/"
    const val EVERYTHING = VERSION + "everything"
    const val TOP_HEADLINES = VERSION + "top-headlines"
}

object ApiParameters {
    const val COUNTRY = "country"
    const val CATEGORY = "category"
    const val API_KEY = "apiKey"
    const val SOURCES = "sources"
    const val Q = "q"
    const val FROM = "from"
    const val SORT_BY = "sortBy"
    const val DOMAINS = "domains"
}
