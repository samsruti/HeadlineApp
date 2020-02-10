package com.samsruti.headlineapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samsruti.headlineapp.domain.Source
import com.samsruti.headlineapp.util.formatDate

@Entity(tableName = "articles")
data class Article constructor(
    @Embedded
    val source: Source,
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val title: String,
    val urlToImage: String?
){
    val publishedDate: String
        get() = publishedAt.formatDate()
}