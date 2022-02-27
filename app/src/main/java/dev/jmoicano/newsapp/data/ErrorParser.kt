package dev.jmoicano.newsapp.data

import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ErrorParser @Inject constructor(private val retrofit: Retrofit) {
    fun <T> parseError(response: Response<T>): ApiError {
        val converter = retrofit.responseBodyConverter<ApiError>(ApiError::class.java, arrayOf())
        return response.errorBody()?.let { errorBody ->
            converter.convert(errorBody)
        }?: ApiError("unknown_status", "unknown_code", "Unkown error")
    }
}