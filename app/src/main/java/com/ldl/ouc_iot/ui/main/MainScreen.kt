package com.ldl.ouc_iot.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ldl.ouc_iot.AppContainer
import com.ldl.ouc_iot.ui.State
import com.ldl.ouc_iot.ui.login.LoginPage
import com.ldl.ouc_iot.ui.login.LoginViewModel
import com.ldl.ouc_iot.ui.user.UserPage
import com.ldl.ouc_iot.ui.user.UserViewModel


@Composable
fun MainScreen(container: AppContainer) {
    val loginViewModel: LoginViewModel =
        viewModel(factory = LoginViewModel.provideFactory(container.loginRepository))
    val userViewModel: UserViewModel =
        viewModel(factory = UserViewModel.provideFactory(container.userRepository))
    val uiState by loginViewModel.uiState.collectAsState()

    if (uiState.loginState is State.Success) {
        userViewModel.update()
        UserPage(userViewModel)
    } else {
        LoginPage(loginViewModel)

    }

}

