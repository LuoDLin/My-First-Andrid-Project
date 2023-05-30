package com.ldl.ouc_iot.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ldl.ouc_iot.repository.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    data class UserPageUiState(val phone: String, val username: String)

    private val _userPageUiState = MutableStateFlow(UserPageUiState("", ""))
    val uiState = _userPageUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.userInfo.collect { user ->
                _userPageUiState.update {
                    it.copy(phone = user.phone, username = user.userName)
                }

            }
        }
    }

    fun update() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.sync()
            }
        }

    }


    companion object {
        fun provideFactory(
            repository: UserRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(repository) as T
            }
        }
    }
}