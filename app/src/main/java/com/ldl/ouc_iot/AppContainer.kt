package com.ldl.ouc_iot

import android.content.Context
import com.ldl.ouc_iot.datasource.local.LocalDataSource
import com.ldl.ouc_iot.datasource.local.FakeLocalDataSource
import com.ldl.ouc_iot.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.datasource.remote.FakeNetworkDataSource
import com.ldl.ouc_iot.repository.login.LoginRepository
import com.ldl.ouc_iot.repository.login.LoginRepositoryImpl
import com.ldl.ouc_iot.repository.user.UserRepository
import com.ldl.ouc_iot.repository.user.UserRepositoryImpl

interface AppContainer {
    val loginRepository: LoginRepository
    val userRepository: UserRepository
}


class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    private val localDataSource: LocalDataSource by lazy { FakeLocalDataSource() }
//    private val networkDataSource: NetworkDataSource by lazy { HttpNetworkDataSource(RetrofitClient.apiService) }
    private val networkDataSource: NetworkDataSource by lazy { FakeNetworkDataSource() }
    override val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(localDataSource, networkDataSource)
    }
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(localDataSource, networkDataSource, loginRepository)
    }
}