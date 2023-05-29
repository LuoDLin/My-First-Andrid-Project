package com.ldl.ouc_iot.data.datasource.remote.impl

import com.ldl.ouc_iot.data.Result
import com.ldl.ouc_iot.data.datasource.remote.NetworkDataSource
import com.ldl.ouc_iot.data.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.data.datasource.remote.entities.PhoneCode
import kotlinx.coroutines.delay

class FakeNetworkDataSource : NetworkDataSource {
    override suspend fun getCode(phone: String): Result<PhoneCode> {
        delay(1500)

        return Result.Success(PhoneCode(phone))
//        else Re?sult.Error(Exception("获取验证码出错"))
    }

    override suspend fun phoneLogin(phone: String, code: String): Result<NetworkLogin> {
        delay(1500)
        return if (phone == "17608316624" && code == "111111") {
            Result.Success(
                NetworkLogin(
                    code = 200, userName = phone, token = "zjoajfioaf/.zfjasiofa21sdz=234io"
                )
            )
        } else {
            Result.Error(Exception("验证码错误"))
        }
    }

    override suspend fun passwordLogin(phone: String, password: String): Result<NetworkLogin> {
        delay(1500)
        return if (phone == "17608316624" && password == "11111111") {
            Result.Success(
                NetworkLogin(
                    code = 200, userName = phone, token = "zjoajfioaf/.zfjasiofa21sdz=234io"
                )
            )
        } else {
            Result.Error(Exception("密码错误"))
        }
    }

    override suspend fun logout(phone: String): Result<NetworkLogin> {
        delay(1500)
        return Result.Success(NetworkLogin())
    }

}