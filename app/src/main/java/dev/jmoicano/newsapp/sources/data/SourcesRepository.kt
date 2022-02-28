package dev.jmoicano.newsapp.sources.data

import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sources.data.models.SourceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SourcesRepository @Inject constructor(private val sourcesDataSource: SourcesDataSource) {
    suspend fun fetchSourcesList(): Flow<Result<List<SourceResponse>>> {
        return flow {
            emit(Result.Loading)
            emit(sourcesDataSource.getDataSourcesList())
        }.flowOn(Dispatchers.IO)
    }
}