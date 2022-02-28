package dev.jmoicano.newsapp.articles.data.models

import com.google.gson.annotations.SerializedName

data class ArticleSourceResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String

)
