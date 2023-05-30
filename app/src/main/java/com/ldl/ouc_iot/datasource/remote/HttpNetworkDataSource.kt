package com.ldl.ouc_iot.datasource.remote

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.datasource.remote.entities.NetworkUser
import com.ldl.ouc_iot.datasource.remote.entities.PhoneCode
import com.ldl.ouc_iot.datasource.remote.retrofit.ApiService
import com.ldl.ouc_iot.datasource.remote.retrofit.LoginUserInfo
import kotlinx.coroutines.delay

class HttpNetworkDataSource(private val apiService: ApiService) : NetworkDataSource {
    private val tag = "HttpNetworkDataSource"
    override suspend fun getCode(phone: String): Result<PhoneCode> {
        delay(500)
        return try {
            val code = apiService.getCode(phone, 0)
            Result.Success(code)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun phoneLogin(phone: String, code: String): Result<NetworkLogin> {
        return try {
            val login = apiService.login(LoginUserInfo(phone = phone, validCode = code, type = 1))
            Result.Success(login)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun passwordLogin(phone: String, password: String): Result<NetworkLogin> {
        return try {
            val login =
                apiService.login(LoginUserInfo(phone = phone, password = password, type = 2))
            Result.Success(login)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logout(phone: String): Result<NetworkLogin> {
        return Result.Success(NetworkLogin())
    }

    override suspend fun getUserInfo(token: String): Result<NetworkUser> {
//        apiService.
        return Result.Error(Exception("未开放"))
    }
}