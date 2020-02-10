package com.samsruti.headlineapp.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samsruti.headlineapp.model.Article

@Dao
interface HeadlinesAppDao {

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getHeadlines(): DataSource.Factory<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllHeadlines(headlines: List<Article>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(single: Article)

}