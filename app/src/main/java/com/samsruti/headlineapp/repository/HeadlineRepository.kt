package com.samsruti.headlineapp.repository

import androidx.paging.LivePagedListBuilder
import com.samsruti.headlineapp.api.NewsApiService
import com.samsruti.headlineapp.model.ArticlesFeedResult

class HeadlineRepository(
    private val apiService: NewsApiService,
    private val cache: HeadlineLocalCache
){

//Todo ADd country as parameter
    fun fetchArticle(country: String): ArticlesFeedResult {
        val dataSourceFactory = cache.getArticle()
        val boundaryCallback = ArticleBoundaryCallback(country,apiService, cache)
        val networkError = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ArticlesFeedResult(data, networkError)

    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }

}