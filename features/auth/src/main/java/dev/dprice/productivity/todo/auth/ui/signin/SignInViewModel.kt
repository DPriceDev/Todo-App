package dev.dprice.productivity.todo.auth.ui.signin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface SignInViewModel {
}

@HiltViewModel
class SignInViewModelImpl @Inject constructor() : ViewModel(), SignInViewModel {

}