package com.ldl.ouc_iot.datasource.remote

import com.ldl.ouc_iot.Result
import com.ldl.ouc_iot.datasource.remote.entities.NetworkLogin
import com.ldl.ouc_iot.datasource.remote.entities.NetworkUser
import com.ldl.ouc_iot.datasource.remote.entities.PhoneCode
import kotlinx.coroutines.delay

class FakeNetworkDataSource : NetworkDataSource {
    override suspend fun getCode(phone: String): Result<PhoneCode> {
        delay(1500)

        return Result.Success(PhoneCode(200))
//        else Re?sult.Error(Exception("获取验证码出错"))
    }

    override suspend fun phoneLogin(phone: String, code: String): Result<NetworkLogin> {
        delay(1500)
        return if (phone == "17608316624" && code == "111111") {
            Result.Success(
                NetworkLogin(
                    code = 200, message = phone, token = "zjoajfioaf/.zfjasiofa21sdz=234io"
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
                    code = 200, message = phone, token = "zjoajfioaf/.zfjasiofa21sdz=234io"
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

    override suspend fun getUserInfo(token: String): Result<NetworkUser> {
        delay(2000)
        return Result.Success(NetworkUser("罗德林", "1008611"))
    }

}