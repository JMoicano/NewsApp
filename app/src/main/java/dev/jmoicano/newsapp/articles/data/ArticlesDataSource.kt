package dev.jmoicano.newsapp.articles.data

import dev.jmoicano.newsapp.articles.data.models.ArticleResponse
import dev.jmoicano.newsapp.data.Result

interface ArticlesDataSource {
    suspend fun getArticlesList(
        sourceId: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleResponse>>
}