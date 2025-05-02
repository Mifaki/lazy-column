package com.brawijaya.lazycolumn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.brawijaya.lazycolumn.model.User
import com.brawijaya.lazycolumn.network.ApiService
import com.brawijaya.lazycolumn.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface UserUiState {
    data class Success(val users: List<User>) : UserUiState
    data object Error : UserUiState
    data object Loading : UserUiState
}

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            userRepository.getUsers().fold(
                onSuccess = { users ->
                    _uiState.update { UserUiState.Success(users) }
                },
                onFailure = {
                    _uiState.update { UserUiState.Error }
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = UserRepository(ApiService.create())
                UserViewModel(repository)
            }
        }
    }
}