package dev.jmoicano.newsapp.sourceslist.data

import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse

interface SourcesListDataSource {
    suspend fun getDataSourcesList(): Result<List<SourceResponse>>
}