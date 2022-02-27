package dev.jmoicano.newsapp.sourceslist.data

import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SourcesListRepository @Inject constructor(private val sourcesListDataSource: SourcesListDataSource) {
    suspend fun fetchSourcesList(): Flow<Result<List<SourceResponse>>> {
        return flow {
            emit(Result.Loading)
            emit(sourcesListDataSource.getDataSourcesList())
        }.flowOn(Dispatchers.IO)
    }
}