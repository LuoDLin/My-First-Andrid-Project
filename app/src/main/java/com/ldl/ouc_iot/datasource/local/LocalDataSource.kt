package com.ldl.ouc_iot.datasource.local

import com.ldl.ouc_iot.datasource.local.entities.LocalLogin

interface LocalDataSource {
    suspend fun saveLoginInfo(info: LocalLogin)
    fun getLoginInfo() : LocalLogin
}