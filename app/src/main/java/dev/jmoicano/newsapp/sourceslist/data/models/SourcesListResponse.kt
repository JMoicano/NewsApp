package dev.jmoicano.newsapp.sourceslist.data.models

import com.google.gson.annotations.SerializedName

data class SourcesListResponse(
    @SerializedName("sources") val sources: List<SourceResponse>
)
