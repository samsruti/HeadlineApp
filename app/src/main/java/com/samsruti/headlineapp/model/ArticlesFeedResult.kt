package com.samsruti.headlineapp.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class ArticlesFeedResult(
    val data: LiveData<PagedList<Article>>,
    val error: LiveData<String>
)