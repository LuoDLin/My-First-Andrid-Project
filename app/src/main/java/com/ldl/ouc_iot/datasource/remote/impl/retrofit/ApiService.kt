package com.ldl.ouc_iot.datasource.remote.impl.retrofit

import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.datasource.remote.entities.PhoneCode
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginUserInfo(
    val phone: String, val password: String? = null, val validCode: String? = null, val type: Int
)

interface ApiService {
    /**
     * 获取验证码
     */
    @GET("valid")
    suspend fun getCode(
        @Query("phone") phone: String, @Query("type") type: Int
    ): PhoneCode

    /**
     * 登录
     */
    @POST("user/login")
    suspend fun login(@Body info: LoginUserInfo): NetworkLogin

    /**
     * 退出
     */
    suspend fun logout(phone: String): NetworkLogin
}