package com.ldl.ouc_iot.repository.user

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.local.LocalDataSource
import com.ldl.ouc_iot.datasource.local.entities.LocalUser
import com.ldl.ouc_iot.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.repository.login.LoginRepository
import com.ldl.ouc_iot.repository.login.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource,
    private val loginRepository: LoginRepository,
) : UserRepository {

    private val _userInfo = MutableStateFlow(localDataSource.getUserInfo())
    private var token = ""
    override val userInfo: Flow<LocalUser> = _userInfo

    init {

        CoroutineScope(Dispatchers.IO).launch {
            loginRepository.loginSate.collect {
                token = if (it is LoginState.LoginSuccess) {
                    it.data.token
                } else {
                    ""
                }
            }
        }
    }

    override suspend fun sync() {
        if (token.isNotEmpty()) {
            val user = networkDataSource.getUserInfo(token)
            if (user is Result.Success) {
                _userInfo.emit(user.data.run {
                    LocalUser(userName = userName, phone = phone)
                })
            }
        }


    }
}