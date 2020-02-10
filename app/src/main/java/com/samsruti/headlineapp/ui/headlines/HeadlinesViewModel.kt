package com.samsruti.headlineapp.ui.headlines

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.samsruti.headlineapp.domain.ApiStatus

import com.samsruti.headlineapp.model.Article
import com.samsruti.headlineapp.model.ArticlesFeedResult
import com.samsruti.headlineapp.repository.HeadlineRepository
import com.samsruti.headlineapp.viewmodel.BaseViewModel
import kotlinx.coroutines.launch


class HeadlinesViewModel(private val repository: HeadlineRepository) : BaseViewModel() {

    class Factory(private val repository: HeadlineRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HeadlinesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }



    private val countryLiveData = MutableLiveData<String>()
    private val feedResult: LiveData<ArticlesFeedResult> = Transformations.map(countryLiveData){
        repository.fetchArticle(it)
    }

    val articles: LiveData<PagedList<Article>> = Transformations.switchMap(feedResult){
        it.data
    }

    val networkErrors: LiveData<String> = Transformations.switchMap(feedResult){
        it.error
    }

    fun fetchArticle(country: String){
        countryLiveData.postValue(country)
    }

    private val _navigateToSelectedNews = MutableLiveData<Article>()

    val navigateToSelectedNetworkNews: LiveData<Article>
        get() = _navigateToSelectedNews



//    init {
//        mainScope.launch {
//            try {
//                fetchArticle(countryLiveData.value?: "in")
//            }catch (e: Exception){
////                Timber.e("Error: $e")
//                _status.value = ApiStatus.ERROR
//            }
//        }
//    }

    fun displayNewsDetails(headline: Article) {
        _navigateToSelectedNews.value = headline
    }

    fun displayNewsDetailsComplete() {
        _navigateToSelectedNews.value = null
    }


}
