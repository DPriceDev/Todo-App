package dev.dprice.productivity.todo.auth.verify.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dprice.productivity.todo.auth.library.model.ResendCodeResponse
import dev.dprice.productivity.todo.auth.library.model.VerifyUserResponse
import dev.dprice.productivity.todo.auth.usecases.ResendVerificationCodeUseCase
import dev.dprice.productivity.todo.auth.usecases.VerifySignUpCodeUseCase
import dev.dprice.productivity.todo.auth.verify.model.VerifyErrorState
import dev.dprice.productivity.todo.auth.verify.model.VerifyState
import dev.dprice.productivity.todo.ui.components.ButtonState
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface VerifyCodeViewModel {
    val viewState: VerifyState

    fun updateCode(code: String, focus: Boolean)

    fun onSubmit(username: String, goToMainApp: () -> Unit)

    fun resendVerificationCode(username: String)
}

@HiltViewModel
class VerifyCodeViewModelImpl @Inject constructor(
    private val verifyUserCodeUpdater: VerifyUserCodeUpdater,
    private val verifySignUpCodeUseCase: VerifySignUpCodeUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase
) : ViewModel(),
    VerifyCodeViewModel {

    private val mutableViewState: MutableState<VerifyState> = mutableStateOf(VerifyState())
    override val viewState: VerifyState by mutableViewState

    override fun updateCode(code: String, focus: Boolean) {
        val updatedCode = verifyUserCodeUpdater.updateCode(viewState.code, code, focus)
        mutableViewState.value = viewState.copy(
            code = updatedCode,
            buttonState = if (updatedCode.isValid) ButtonState.ENABLED else ButtonState.DISABLED
        )
    }

    override fun onSubmit(username: String, goToMainApp: () -> Unit) {
        if(!viewState.code.isValid) return

        mutableViewState.value = viewState.copy(buttonState = ButtonState.LOADING)

        viewModelScope.launch {
            val response = verifySignUpCodeUseCase.invoke(
                viewState.code.value,
                user = username
            )

            when (response) {
                VerifyUserResponse.Done -> goToMainApp()
                is VerifyUserResponse.Error -> {
                    mutableViewState.value = viewState.copy(
                        errorState = VerifyErrorState.Error("error!"),
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
            when(response) {
                ResendCodeResponse.Done -> Timber.i("Code Resent")
                is ResendCodeResponse.Error -> Timber.e(response.throwable, "Code resend error!")
            }
        }
    }
}