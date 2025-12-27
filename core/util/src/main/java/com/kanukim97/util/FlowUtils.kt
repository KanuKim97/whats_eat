package com.kanukim97.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.withLoading(
    updateLoadingState: (Boolean) -> Unit
): Flow<T> {
    return this
        .onStart { updateLoadingState(true) }
        .onCompletion { updateLoadingState(false) }
}