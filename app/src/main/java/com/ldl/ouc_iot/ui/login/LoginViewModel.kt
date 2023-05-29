package com.ldl.ouc_iot.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ldl.ouc_iot.data.Result
import com.ldl.ouc_iot.data.repository.login.LoginRepository
import com.ldl.ouc_iot.data.repository.login.LoginState
import com.ldl.ouc_iot.ui.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val loginState: State<Nothing>,
    val phoneCodeState: State<Nothing>,
)


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState(State.None, State.None))

    val uiState = _loginUiState.asStateFlow()

    init {
        viewModelScope.launch {
            loginRepository.loginSate.collect { loginState ->
                when (loginState) {
                    //登陆中
                    LoginState.LoggingIn -> _loginUiState.update { it.copy(loginState = State.Loading) }
                    //登录失败
                    is LoginState.LoginFailure -> _loginUiState.update {
                        it.copy(loginState = State.Failed(loginState.errorMessage))
                    }
                    //登录成功
                    is LoginState.LoginSuccess -> _loginUiState.update {
                        it.copy(loginState = State.Success())
                    }
                    //正常状态
                    LoginState.NotLoggedIn -> _loginUiState.update {
                        it.copy(loginState = State.None, phoneCodeState = State.None)
                    }
                }
            }
        }
    }

    fun getPhoneCode(phone: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(phoneCodeState = State.Loading) }
            loginRepository.getPhoneCode(phone).apply {
                when (this) {
                    //获取验证码失败
                    is Result.Error -> _loginUiState.update {
                        Log.i("--", "获取验证码失败")
                        it.copy(phoneCodeState = State.Failed("获取验证码失败"))
                    }
                    //获取验证码成功
                    is Result.Success -> _loginUiState.update {
                        Log.i("--", "获取验证码成功")
                        it.copy(phoneCodeState = State.Success())
                    }
                }
            }
        }
    }

    fun phoneLogin(phone: String, code: String) {
        viewModelScope.launch {
            loginRepository.phoneLogin(phone, code)
        }
    }


    /**
     * 密码登录
     */
    fun passwordLogin(phone: String, password: String) {

    }

    /**
     * 退出
     */
    fun logout() {

    }

    companion object {
        fun provideFactory(
            repository: LoginRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(repository) as T
            }
        }
    }
}