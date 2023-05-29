package com.ldl.ouc_iot.data

import android.content.Context
import com.ldl.ouc_iot.data.datasource.local.LocalDataSource
import com.ldl.ouc_iot.data.datasource.local.impl.FakeLocalDataSource
import com.ldl.ouc_iot.data.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.data.datasource.remote.impl.FakeNetworkDataSource
import com.ldl.ouc_iot.data.repository.login.LoginRepository
import com.ldl.ouc_iot.data.repository.login.impl.LoginRepositoryImpl

interface AppContainer {
    val loginRepository: LoginRepository
}


class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val loginRepository: LoginRepository by lazy {
        val localDataSource: LocalDataSource = FakeLocalDataSource()
        val networkDataSource: NetworkDataSource = FakeNetworkDataSource()
        LoginRepositoryImpl(localDataSource, networkDataSource)
    }
}