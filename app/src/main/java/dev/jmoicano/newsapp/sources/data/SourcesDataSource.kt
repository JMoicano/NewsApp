package dev.jmoicano.newsapp.sources.data

import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sources.data.models.SourceResponse

interface SourcesDataSource {
    suspend fun getDataSourcesList(): Result<List<SourceResponse>>
}