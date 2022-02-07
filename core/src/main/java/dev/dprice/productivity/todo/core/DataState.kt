package dev.dprice.productivity.todo.core

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()

    data class Data<T>(val value: T) : DataState<T>()

    data class Error(val throwable: Throwable) : DataState<Nothing>()
}
