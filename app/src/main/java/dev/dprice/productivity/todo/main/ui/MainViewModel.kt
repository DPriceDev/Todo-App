package dev.dprice.productivity.todo.main.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface MainViewModel {
}

@HiltViewModel
class MainViewModelImpl @Inject constructor() : ViewModel(), MainViewModel {

}