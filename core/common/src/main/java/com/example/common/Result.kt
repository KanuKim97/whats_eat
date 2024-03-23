package com.example.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T): Result<T>

    data class Error(val error: Throwable? = null): Result<Nothing>

    data object IsLoading: Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { type -> Result.Success(type) }
        .onStart { emit(Result.IsLoading) }
        .catch { error -> emit(Result.Error(error)) }
}