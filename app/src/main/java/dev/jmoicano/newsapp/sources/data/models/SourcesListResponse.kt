package dev.jmoicano.newsapp.sources.data.models

import com.google.gson.annotations.SerializedName

data class SourcesListResponse(
    @SerializedName("sources") val sources: List<SourceResponse>
)
