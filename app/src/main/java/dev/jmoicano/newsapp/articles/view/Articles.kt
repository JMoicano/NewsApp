package dev.jmoicano.newsapp.articles.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.jmoicano.newsapp.articles.data.models.ArticleResponse

@Composable
fun ArticlesList(sourceId: String) {
    val viewModel = hiltViewModel<ArticlesViewModel>()
    val articles = viewModel.getArticlesPagination(sourceId).collectAsLazyPagingItems()
    LazyColumn {
        items(articles) { article ->
            article?.let { Article(article = article) }
        }
    }
}

@Composable
fun Article(article: ArticleResponse) {
    Text(text = article.title)
}