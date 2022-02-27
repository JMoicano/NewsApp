package dev.jmoicano.newsapp.data

sealed class Result<out R> {
    object None : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<out T>(val response: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}
