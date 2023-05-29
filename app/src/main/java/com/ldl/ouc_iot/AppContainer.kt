package com.ldl.ouc_iot

import android.content.Context
import com.ldl.ouc_iot.datasource.local.LocalDataSource
import com.ldl.ouc_iot.datasource.local.impl.FakeLocalDataSource
import com.ldl.ouc_iot.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.datasource.remote.impl.retrofit.HttpNetworkDataSource
import com.ldl.ouc_iot.datasource.remote.impl.retrofit.RetrofitClient
import com.ldl.ouc_iot.repository.login.LoginRepository
import com.ldl.ouc_iot.repository.login.impl.LoginRepositoryImpl

interface AppContainer {
    val loginRepository: LoginRepository
}


class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val loginRepository: LoginRepository by lazy {
        val localDataSource: LocalDataSource = FakeLocalDataSource()
        val networkDataSource: NetworkDataSource = HttpNetworkDataSource(RetrofitClient.apiService)
        LoginRepositoryImpl(localDataSource, networkDataSource)
    }
}