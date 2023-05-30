package com.ldl.ouc_iot.datasource.local

import com.ldl.ouc_iot.datasource.local.entities.LocalLogin
import com.ldl.ouc_iot.datasource.local.entities.LocalUser
import kotlinx.coroutines.delay

class FakeLocalDataSource() : LocalDataSource {


    override suspend fun saveLoginInfo(info: LocalLogin) {
        //        //保存----
        delay(1000)
        //延时表示保存中
    }

    override fun getLoginInfo(): LocalLogin {
        return LocalLogin("", "")
    }

    override fun getUserInfo(): LocalUser {
        return LocalUser("LuoDLin", "17608316624")
    }

    override fun saveUserInfo() {

    }


}
