package dev.jmoicano.newsapp.articles.data.remote

import dev.jmoicano.newsapp.articles.data.models.ArticlesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesAPI {
    @GET("/v2/everything")
    suspend fun getArticles(
        @Query("sources") soruceId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ArticlesListResponse>
}