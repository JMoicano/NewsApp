package dev.jmoicano.newsapp.articles.data.models

import com.google.gson.annotations.SerializedName

data class ArticlesListResponse(
    @SerializedName("articles") val articles: List<ArticleResponse>
)
