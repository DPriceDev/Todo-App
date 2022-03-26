package dev.dprice.productivity.todo.auth.verify.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface VerifyCodeViewModel {
    val canSubmit: Boolean

    fun onSubmit()
}

@HiltViewModel
class VerifyCodeViewModelImpl @Inject constructor() : ViewModel(), VerifyCodeViewModel {

    override val canSubmit: Boolean = false // todo: on full code

    override fun onSubmit() {
        // TODO("Not yet implemented")
    }
}