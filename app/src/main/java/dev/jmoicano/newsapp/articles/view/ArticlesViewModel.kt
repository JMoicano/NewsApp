package dev.jmoicano.newsapp.articles.view

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jmoicano.newsapp.articles.data.ArticlesDataSource
import dev.jmoicano.newsapp.articles.data.ArticlesRepository
import dev.jmoicano.newsapp.articles.data.models.ArticleResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val articlesDataSource: ArticlesDataSource) :
    ViewModel() {

    fun getArticlesPagination(sourceId: String): Flow<PagingData<ArticleResponse>> {
        return Pager(PagingConfig(pageSize = 20)) {
            ArticlesRepository(articlesDataSource, sourceId)
        }.flow
    }
}