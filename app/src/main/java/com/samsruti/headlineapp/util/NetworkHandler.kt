package com.samsruti.headlineapp.util

import com.samsruti.headlineapp.BuildConfig
import com.samsruti.headlineapp.api.ArticleApiResponse
import com.samsruti.headlineapp.api.NewsApiService
import com.samsruti.headlineapp.model.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

fun fetchCountryHeadlines(
    service: NewsApiService,
    country: String,
    page: Int,
    itemsPerPage: Int,
    onSuccess: (articles: List<Article>) -> Unit,
    onError: (error: String) -> Unit
) {

    val API_KEY = BuildConfig.NEWS_API_KEY

    service.getTopHeadlines(
        country = country,page = page, pageSize = itemsPerPage,apiKey = API_KEY).enqueue(
        object : Callback<ArticleApiResponse> {
            override fun onFailure(call: Call<ArticleApiResponse>?, t: Throwable) {
                Timber.i("fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<ArticleApiResponse>?,
                response: Response<ArticleApiResponse>
            ) {
               Timber.i("Success response $response")
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    onSuccess(articles)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}