package dev.jmoicano.newsapp.sourceslist.data.remote

import dev.jmoicano.newsapp.data.ErrorParser
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.SourcesListDataSource
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse
import javax.inject.Inject

class SourcesListRemoteDataSource (
    private val sourcesListAPI: SourcesListAPI,
    private val errorParser: ErrorParser
) :
    SourcesListDataSource {
    override suspend fun getDataSourcesList(): Result<List<SourceResponse>> {
        val response = sourcesListAPI.getSources()
        return if (response.isSuccessful) {
            Result.Success(response.body()?.sources.orEmpty())
        } else {
            val error = errorParser.parseError(response)
            Result.Error(error.message)
        }
    }
}