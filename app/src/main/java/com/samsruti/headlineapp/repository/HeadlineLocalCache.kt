package com.samsruti.headlineapp.repository

import androidx.paging.DataSource
import com.samsruti.headlineapp.database.HeadlinesAppDao
import com.samsruti.headlineapp.model.Article
import java.util.concurrent.Executor

class HeadlineLocalCache (private val appDao: HeadlinesAppDao, private val ioExecutor: Executor){

    fun insert(articles: List<Article>, insertCompleted: () -> Unit){
        ioExecutor.execute {
            appDao.insertAllHeadlines(articles)
            insertCompleted()
        }
    }

    fun getArticle(): DataSource.Factory<Int, Article>{
        return appDao.getHeadlines()
    }
}