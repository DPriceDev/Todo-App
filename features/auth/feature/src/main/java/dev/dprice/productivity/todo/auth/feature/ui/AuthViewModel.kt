package dev.dprice.productivity.todo.auth.feature.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent
import javax.inject.Inject

interface AuthViewModel {
    val navigationComponents: Set<@JvmSuppressWildcards AuthNavigationComponent>
}

@HiltViewModel
class AuthViewModelImpl @Inject constructor(
    override val navigationComponents: Set<@JvmSuppressWildcards AuthNavigationComponent>
) : ViewModel(),
    AuthViewModel