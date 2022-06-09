package dev.dprice.productivity.todo.main.model

import dev.dprice.productivity.todo.auth.data.model.Session

data class MainState(
    val isLoading: Boolean = false,
    val userSession: Session? = null
)
