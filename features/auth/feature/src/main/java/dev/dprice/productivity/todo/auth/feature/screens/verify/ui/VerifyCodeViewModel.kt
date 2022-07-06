package dev.dprice.productivity.todo.auth.feature.screens.verify.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.data.model.ResendCode
import dev.dprice.productivity.todo.auth.data.model.VerifyUser
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyCodeEvent
import dev.dprice.productivity.todo.auth.feature.screens.verify.model.VerifyState
import dev.dprice.productivity.todo.auth.feature.usecase.UpdateCodeEntryUseCase
import dev.dprice.productivity.todo.auth.usecases.ResendVerificationCodeUseCase
import dev.dprice.productivity.todo.auth.usecases.VerifySignUpCodeUseCase
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface VerifyCodeViewModel {
    val viewState: VerifyState

    fun updateCode(event: VerifyCodeEvent)

    fun onSubmit(username: String, goToMainApp: () -> Unit)

    fun resendVerificationCode(username: String)
}

@HiltViewModel
class VerifyCodeViewModelImpl @Inject constructor(
    private val updateCodeEntryUseCase: UpdateCodeEntryUseCase,
    private val verifySignUpCodeUseCase: VerifySignUpCodeUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase
) : ViewModel(),
    VerifyCodeViewModel {

    private val mutableViewState: MutableState<VerifyState> = mutableStateOf(VerifyState())
    override val viewState: VerifyState by mutableViewState

    override fun updateCode(event: VerifyCodeEvent) {
        val updatedCode = when (event) {
            is VerifyCodeEvent.UpdateCodeFocus -> updateCodeEntryUseCase(
                viewState.code,
                event.focus
            )
            is VerifyCodeEvent.UpdateCodeValue -> updateCodeEntryUseCase(
                viewState.code,
                event.value
            )
        }

        mutableViewState.value = viewState.copy(
            code = updatedCode,
            buttonState = if (updatedCode.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    override fun onSubmit(username: String, goToMainApp: () -> Unit) {
        if (!viewState.code.isValid) return

        mutableViewState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = verifySignUpCodeUseCase.invoke(
                viewState.code.value,
                user = username
            )

            when (response) {
                VerifyUser.Done -> goToMainApp()
                is VerifyUser.Error -> {
                    mutableViewState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_unknown_error),
                        buttonState = ButtonState.ENABLED
                    )
                }
                VerifyUser.ConnectionError -> {
                    mutableViewState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_no_internet),
                        buttonState = ButtonState.ENABLED
                    )
                }
                VerifyUser.ExpiredCode -> {
                    mutableViewState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_expired_code),
                        buttonState = ButtonState.ENABLED
                    )
                }
                VerifyUser.IncorrectCode -> {
                    mutableViewState.value = viewState.copy(
                        errorState = ErrorState.Message(R.string.error_invalid_code),
                        buttonState = ButtonState.ENABLED
                    )
                }
            }
        }
    }

    override fun resendVerificationCode(username: String) {
        viewModelScope.launch {
            // todo: reflect in UI?
            val response = resendVerificationCodeUseCase(username)
            when (response) {
                ResendCode.Done -> Timber.i("Code Resent")
                is ResendCode.Error -> Timber.e(response.throwable, "Code resend error!")
                else -> { /*todo: fill out other options */ }
            }
        }
    }
}
