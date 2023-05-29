package com.ldl.ouc_iot.data.repository.login

import com.ldl.ouc_iot.data.Result
import com.ldl.ouc_iot.data.datasource.local.entities.LocalLogin
import com.ldl.ouc_iot.data.datasource.remote.entities.PhoneCode
import kotlinx.coroutines.flow.Flow


sealed interface LoginState {
    object NotLoggedIn : LoginState
    object LoggingIn : LoginState
    data class LoginSuccess(val token: LocalLogin) : LoginState
    data class LoginFailure(val errorMessage: String) : LoginState
}

interface LoginRepository {

    public val loginSate: Flow<LoginState>

    /**
     * 获取验证码
     */
    suspend fun getPhoneCode(phone: String): Result<PhoneCode>

    /**
     * 验证码登录
     */
    suspend fun phoneLogin(phone: String, code: String)

    /**
     * 密码登录
     */
    suspend fun passwordLogin(phone: String, password: String)

    /**
     * 退出
     */
    suspend fun logout()

}