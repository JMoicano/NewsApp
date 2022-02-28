package dev.jmoicano.newsapp.articles.data.models

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class ArticleResponse(
    @SerializedName("source") val source: ArticleSourceResponse,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: Instant,
    @SerializedName("content") val content: String?
)
