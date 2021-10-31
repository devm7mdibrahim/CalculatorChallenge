package com.devm7mdibrahim.data.util

import com.devm7mdibrahim.domain.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout

const val CACHE_TIMEOUT = 30000L

/**
 * this method will take the method that we will do and handle it in a safe way
 * the operation will run on UI thread
 * if there is an exception will check its type and return custom exceptions
 */
suspend fun <T> safeCacheCall(
    cacheCall: suspend () -> T?
): Flow<DataState<T>> = flow {
    withTimeout(CACHE_TIMEOUT) {
        val response = cacheCall.invoke()

        if (response != null) {
            emit(DataState.Success(response))
        } else {
            emit(DataState.Error(LocalExceptions.UnknownException))
        }
    }
}.catch {
    when (it) {
        is TimeoutCancellationException -> {
            emit(DataState.Error(LocalExceptions.TimeoutException))
        }
        else -> {
            emit(DataState.Error(LocalExceptions.UnknownException))
        }
    }
}.flowOn(Dispatchers.IO)