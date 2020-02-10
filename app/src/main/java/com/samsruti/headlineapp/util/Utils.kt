package com.samsruti.headlineapp.util

import com.samsruti.headlineapp.domain.Headline
import com.samsruti.headlineapp.model.Article

fun convertToDomainModel(it: Article): Headline {
    return Headline(
        author = it.author,
        content = it.content,
        description = it.description,
        publishedAt = it.publishedAt,
        source = it.source,
        title = it.title,
        url = it.url,
        urlToImage = it.urlToImage
    )
}

fun convertToDatabaseModel(it: Headline): Article {
    return Article(
        author = it.author,
        content = it.content,
        description = it.description,
        publishedAt = it.publishedAt,
        source = it.source,
        title = it.title,
        url = it.url,
        urlToImage = it.urlToImage
    )
}