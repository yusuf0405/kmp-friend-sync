package org.joseph.friendsync.common.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

val defaultErrorMessage = "Oops, we could not send your request, try later!"

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {

    fun isSuccess() = this is Success

    fun isError() = this is Error

    fun getOrThrow(): T {
        if (this is Error) throw RuntimeException(defaultErrorMessage)
        return this.data ?: throw RuntimeException(defaultErrorMessage)
    }

    fun copy(data: T): Result<T> {
        return when (this) {
            is Error -> this
            is Success -> Success(data)
        }
    }

    fun exceptionMessageOrNull(): String? = when (this) {
        is Error -> this.message
        else -> null
    }

    class Error<T>(message: String) : Result<T>(data = null, message = message)

    class Success<T>(data: T) : Result<T>(data = data, message = null)

    companion object {

        fun <T> defaultError(): Error<T> = Error(defaultErrorMessage)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFailure(action: (message: String) -> Unit): Result<T> {
    contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
    exceptionMessageOrNull()?.let { action(it) }
    return this
}

fun <T> Result<T?>.filterNotNullOrError(): Result<T> {
    val data = this.data
    return if (data == null) Result.defaultError()
    else Result.Success(data)
}

suspend fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> Result.Error(this.message.toString())
        is Result.Success -> {
            if (data == null) Result.defaultError()
            else Result.Success(transform(data))
        }
    }
}