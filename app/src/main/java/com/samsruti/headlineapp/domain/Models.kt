package com.samsruti.headlineapp.domain


import android.os.Parcelable
import com.samsruti.headlineapp.util.formatDate
import kotlinx.android.parcel.Parcelize

enum class ApiStatus {LOADING, DONE, ERROR, UNSUCCESSFUL, UNKNOWN_HOST}

@Parcelize
data class Headline(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
) : Parcelable {
    val publishedDate: String
        get() = publishedAt.formatDate()
}

@Parcelize
data class Source(

    val id: String?,
    val name: String?
) : Parcelable

