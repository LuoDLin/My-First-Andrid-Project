package com.ldl.ouc_iot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.ldl.ouc_iot.ui.login.LoginPage
import com.ldl.ouc_iot.ui.theme.OuciotTheme
import com.ldl.ouc_iot.ui.login.LoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        val container = app.container

        val loginViewModel: LoginViewModel = ViewModelProvider(
            this, factory = LoginViewModel.provideFactory(container.loginRepository)
        )[LoginViewModel::class.java]

        setContent {
            OuciotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LoginPage(loginViewModel)
                }
            }
        }
    }
}