package com.devm7mdibrahim.domain.model

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()
}