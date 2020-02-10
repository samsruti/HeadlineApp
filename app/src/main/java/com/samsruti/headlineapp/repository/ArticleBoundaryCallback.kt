package com.samsruti.headlineapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.samsruti.headlineapp.api.NewsApiService
import com.samsruti.headlineapp.model.Article
import com.samsruti.headlineapp.util.fetchCountryHeadlines
import timber.log.Timber

class ArticleBoundaryCallback (
    private val country: String,
    private val service: NewsApiService,
    private val cache: HeadlineLocalCache
): PagedList.BoundaryCallback<Article>(){

    private var lastPageRequested = 1

    private var isRequestInProgress = false

    companion object{
        private const val NETWORK_PAGE_SIZE = 10
    }

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private fun fetchAndSaveData(country: String){
        if (isRequestInProgress)
            return

        isRequestInProgress = true

        fetchCountryHeadlines(service = service,
            country = country,
            page = lastPageRequested,
            itemsPerPage = NETWORK_PAGE_SIZE,
            onSuccess = {articles: List<Article> ->
                cache.insert(articles){
                    lastPageRequested++
                    isRequestInProgress = false
                }
            },
            onError = {errors ->
                _networkErrors.postValue(errors)
                isRequestInProgress = false
            })

//        TODO: Refactoring for coroutine with paging
//        if (articlesResponse.isSuccessful){
//            val articles = articlesResponse.body()!!.articles
//            cache.insert(articles) {
//                isRequestInProgress = false
//                lastPageRequested++
//            }
//
//        } else {
//            _networkErrors.value = articlesResponse.errorBody().toString()
//            isRequestInProgress = false
//        }
    }

    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded Loaded")
        fetchAndSaveData(country)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        Timber.i("Item Loaded New")
        fetchAndSaveData(country)

    }
}