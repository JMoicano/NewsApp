package dev.jmoicano.newsapp.sources.data.remote

import dev.jmoicano.newsapp.sources.data.models.SourcesListResponse
import retrofit2.Response
import retrofit2.http.GET

interface SourcesAPI {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(): Response<SourcesListResponse>
}