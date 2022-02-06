package dev.dprice.productivity.todo.auth.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface LoginViewModel {

}

@HiltViewModel
class LoginViewModelImpl @Inject constructor() : ViewModel(), LoginViewModel {

}