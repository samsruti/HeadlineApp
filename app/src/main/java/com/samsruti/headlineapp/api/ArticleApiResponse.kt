package com.samsruti.headlineapp.api

import com.samsruti.headlineapp.model.Article
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleApiResponse(
    val status: String,
    val totalResults: Int = 0,
    val articles: List<Article> = emptyList()
)