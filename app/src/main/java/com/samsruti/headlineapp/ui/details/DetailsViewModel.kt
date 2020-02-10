package com.samsruti.headlineapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samsruti.headlineapp.model.Article
import com.samsruti.headlineapp.viewmodel.BaseViewModel

class DetailsViewModel(article: Article) : BaseViewModel() {
    private val _selectedHeadline = MutableLiveData<Article>()

    val selectedHeadline: LiveData<Article>
            get() = _selectedHeadline

    init {
        _selectedHeadline.value = article
    }

    class Factory(
        private val selectedHeadline: Article) : ViewModelProvider.Factory {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                    return DetailsViewModel(selectedHeadline) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
    }
}
