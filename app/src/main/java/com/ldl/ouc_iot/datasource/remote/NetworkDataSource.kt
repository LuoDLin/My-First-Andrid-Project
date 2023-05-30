package com.ldl.ouc_iot.datasource.remote

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.datasource.remote.entities.NetworkUser
import com.ldl.ouc_iot.datasource.remote.entities.PhoneCode

interface NetworkDataSource {
    /**
     * 获取登录验证码
     */
    suspend fun getCode(phone: String): Result<PhoneCode>

    /**
     * 验证码登录
     */
    suspend fun phoneLogin(phone: String, code: String): Result<NetworkLogin>

    /**
     * 密码登录
     */
    suspend fun passwordLogin(phone: String, password: String): Result<NetworkLogin>

    /**
     * 退出
     */
    suspend fun logout(phone: String): Result<NetworkLogin>

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(token: String): Result<NetworkUser>
}