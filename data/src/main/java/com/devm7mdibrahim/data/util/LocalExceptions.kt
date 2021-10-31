package com.devm7mdibrahim.data.util

/**
 * custom exception for the local call
 */
sealed class LocalExceptions : Exception() {
    object UnknownException : LocalExceptions()
    object TimeoutException : LocalExceptions()
}