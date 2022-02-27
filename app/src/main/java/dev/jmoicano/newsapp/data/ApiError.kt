package dev.jmoicano.newsapp.data

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
