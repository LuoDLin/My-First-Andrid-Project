package com.ldl.ouc_iot.data.datasource.local

import com.ldl.ouc_iot.data.datasource.local.entities.LocalLogin

interface LocalDataSource {
    suspend fun saveLoginInfo(info: LocalLogin)
    fun getLoginInfo() : LocalLogin
}