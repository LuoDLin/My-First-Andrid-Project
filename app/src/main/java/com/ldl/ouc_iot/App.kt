package com.ldl.ouc_iot

import android.app.Application
import com.ldl.ouc_iot.data.AppContainer
import com.ldl.ouc_iot.data.AppContainerImpl

class App : Application() {
    // 手动注入依赖
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}