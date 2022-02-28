package dev.jmoicano.newsapp.sources.data.remote

import dev.jmoicano.newsapp.data.ErrorParser
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sources.data.SourcesDataSource
import dev.jmoicano.newsapp.sources.data.models.SourceResponse

class SourcesRemoteDataSource (
    private val sourcesAPI: SourcesAPI,
    private val errorParser: ErrorParser
) :
    SourcesDataSource {
    override suspend fun getDataSourcesList(): Result<List<SourceResponse>> {
        val response = sourcesAPI.getSources()
        return if (response.isSuccessful) {
            Result.Success(response.body()?.sources.orEmpty())
        } else {
            val error = errorParser.parseError(response)
            Result.Error(error.message)
        }
    }
}