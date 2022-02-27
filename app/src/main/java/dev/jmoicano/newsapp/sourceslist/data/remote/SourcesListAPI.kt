package dev.jmoicano.newsapp.sourceslist.data.remote

import dev.jmoicano.newsapp.sourceslist.data.models.SourcesListResponse
import retrofit2.Response
import retrofit2.http.GET

interface SourcesListAPI {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(): Response<SourcesListResponse>
}