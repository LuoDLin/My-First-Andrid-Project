package com.ldl.ouc_iot.data.datasource.local.impl

import com.ldl.ouc_iot.data.datasource.local.LocalDataSource
import com.ldl.ouc_iot.data.datasource.local.entities.LocalLogin
import kotlinx.coroutines.delay

class FakeLocalDataSource() : LocalDataSource {


    override suspend fun saveLoginInfo(info: LocalLogin) {
        //        //保存----
        delay(1000)
        //延时表示保存中
    }

    override fun getLoginInfo(): LocalLogin {
        return  LocalLogin("17608316624","asafasfasd")
    }


}
