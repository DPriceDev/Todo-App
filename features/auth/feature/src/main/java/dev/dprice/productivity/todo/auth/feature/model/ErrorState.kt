package dev.dprice.productivity.todo.auth.feature.model

import androidx.annotation.StringRes

sealed class ErrorState {
    object None : ErrorState()
    data class Message(@StringRes val messageId: Int) : ErrorState()
}
