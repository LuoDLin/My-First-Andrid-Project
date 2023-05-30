package com.ldl.ouc_iot.repository.login

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.local.LocalDataSource
import com.ldl.ouc_iot.datasource.local.entities.LocalLogin
import com.ldl.ouc_iot.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun NetworkLogin.asLocalLogin(): LocalLogin = LocalLogin(userName = message, token = token)


fun NetworkLogin.asLoginState(): LoginState = when (code) {
    200 -> LoginState.LoginSuccess(asLocalLogin())
    201 -> LoginState.LoginFailure("密码错误")
    202 -> LoginState.LoginFailure("服务器异常")
    else -> LoginState.LoginFailure("未知错误")
}


class LoginRepositoryImpl(
    private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : LoginRepository {
    //本地数据源为为一可信
    private val _loginState = MutableStateFlow(localDataSource.getLoginInfo().run {
        if (token.isEmpty() || token.isEmpty()) LoginState.NotLoggedIn
        else LoginState.LoginSuccess(this)
    })
    override val loginSate: Flow<LoginState> = _loginState

    override suspend fun getPhoneCode(phone: String) = networkDataSource.getCode(phone)


    private suspend fun updateState(result: Result<NetworkLogin>) {
        result.apply {
            when (this) {
                is Result.Error -> _loginState.emit(
                    LoginState.LoginFailure(exception.message ?: "未知错误")
                )

                is Result.Success -> _loginState.emit(LoginState.LoginSuccess(data.asLocalLogin()))
            }
        }
    }

    override suspend fun phoneLogin(phone: String, code: String) {
        _loginState.emit(LoginState.LoggingIn)  //登录中
        delay(1000) //延时模拟登录中
        updateState(networkDataSource.phoneLogin(phone, code))
    }

    override suspend fun passwordLogin(phone: String, password: String) {
        _loginState.emit(LoginState.LoggingIn)  //登录中
        delay(1000) //延时模拟登录中
        updateState(networkDataSource.passwordLogin(phone, password))
    }

    override suspend fun logout() {

    }


}