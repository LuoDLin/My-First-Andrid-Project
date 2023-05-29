package com.ldl.ouc_iot.datasource.remote.impl.retrofit

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.datasource.remote.entities.PhoneCode
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
            val login =
                apiService.login(LoginUserInfo(phone = phone, validCode = code, type = 1))
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
}