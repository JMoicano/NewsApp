package dev.jmoicano.newsapp.articles.data.remote

import dev.jmoicano.newsapp.articles.data.ArticlesDataSource
import dev.jmoicano.newsapp.articles.data.models.ArticleResponse
import dev.jmoicano.newsapp.data.ErrorParser
import dev.jmoicano.newsapp.data.Result

class ArticlesRemoteDataSource(
    private val articlesAPI: ArticlesAPI,
    private val errorParser: ErrorParser
) : ArticlesDataSource {
    override suspend fun getArticlesList(
        sourceId: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleResponse>> {
        val response = articlesAPI.getArticles(sourceId, page, pageSize)
        return if (response.isSuccessful) {
            Result.Success(response.body()?.articles.orEmpty())
        } else {
            val error = errorParser.parseError(response)
            Result.Error(error.message)
        }
    }

}