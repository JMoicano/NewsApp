package dev.jmoicano.newsapp.articles.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.jmoicano.newsapp.articles.data.models.ArticleResponse
import dev.jmoicano.newsapp.data.Result
import javax.inject.Inject

class ArticlesRepository (private val articlesDataSource: ArticlesDataSource, val sourceId: String) :
    PagingSource<Int, ArticleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        return try {
            val page = params.key ?: 1
            val articleResponse =
                articlesDataSource.getArticlesList(sourceId = sourceId, page = page, params.loadSize)
            when (articleResponse) {
                is Result.Success -> LoadResult.Page(
                    data = articleResponse.response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page.plus(1)
                )
                is Result.Error -> {
                    LoadResult.Error(Throwable(message = articleResponse.message))
                }
                else -> {
                    LoadResult.Error(Throwable(message = "Unknown error"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition
    }
}