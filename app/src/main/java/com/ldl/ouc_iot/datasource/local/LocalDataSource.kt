package com.ldl.ouc_iot.datasource.local

import com.ldl.ouc_iot.datasource.local.entities.LocalLogin
import com.ldl.ouc_iot.datasource.local.entities.LocalUser

interface LocalDataSource {
    suspend fun saveLoginInfo(info: LocalLogin)
    fun getLoginInfo(): LocalLogin

    fun getUserInfo(): LocalUser
    fun saveUserInfo()
}