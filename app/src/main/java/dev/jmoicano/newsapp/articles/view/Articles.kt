package dev.jmoicano.newsapp.articles.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import dev.jmoicano.newsapp.R
import dev.jmoicano.newsapp.articles.data.models.ArticleResponse
import dev.jmoicano.newsapp.extensions.formatToDate

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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            article.title?.let { title->
                Text(text = title, style = MaterialTheme.typography.h4)
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = article.publishedAt.formatToDate(),
                style = MaterialTheme.typography.caption
            )
            article.author?.let { author ->
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = author,
                    style = MaterialTheme.typography.caption
                )
            }
            Image(
                painter = rememberImagePainter(data = article.urlToImage,
                    builder = {
                    size(OriginalSize)
                }),
                contentDescription = stringResource(
                    R.string.article_thumb
                ),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
            article.description?.let { description ->
                Text(text = description, style = MaterialTheme.typography.body1)
            }
        }
    }
}