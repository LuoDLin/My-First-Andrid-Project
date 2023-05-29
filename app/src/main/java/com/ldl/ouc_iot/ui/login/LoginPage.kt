package com.ldl.ouc_iot.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ldl.ouc_iot.R
import com.ldl.ouc_iot.ui.State


@Composable
fun LoginPage(
    loginViewModel: LoginViewModel
) {//ui状态
    val uiState by loginViewModel.uiState.collectAsState()
    val phoneState = remember { PhoneTextFieldState() }
    val paswdState = remember { PasswordTextFieldState() }

    val codeState = remember { CodeTextFieldState() }
    //是否使用验证码登录
    var codeLogin by remember { mutableStateOf(true) }


    val login = {
        phoneState.enableShowErrors()
        if (phoneState.isValid) {
            if (codeLogin) {
                if (codeState.isValid) loginViewModel.phoneLogin(
                    phoneState.text, codeState.text
                )
            } else {
                if (paswdState.isValid) loginViewModel.passwordLogin(
                    phoneState.text, paswdState.text
                )
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Filled.DeliveryDining,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(stringResource(id = R.string.logo), style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(20.dp))
        //账号输入框
        PhoneTextField(phoneState = phoneState)

        Spacer(modifier = Modifier.height(20.dp))

        if (codeLogin) {
            //验证码输入框
            CodeTextField(uiState = codeState,
                getCode = { loginViewModel.getPhoneCode(phoneState.text) },
                codeState = uiState.phoneCodeState,
                onImeAction = { login() })
        } else {
            //密码输入框
            PasswordTextField(passwordState = paswdState, onImeAction = { login() })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { login() }, modifier = Modifier.fillMaxWidth()) {
            Row {
                if (uiState.loginState == State.Loading) CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(18.dp)
                        .align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    stringResource(id = R.string.login), style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (codeLogin) stringResource(id = R.string.pswdLogin) else stringResource(id = R.string.codeLogin),
            modifier = Modifier.clickable() { codeLogin = !codeLogin },
            style = TextStyle(color = MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.weight(7f))
    }


}

@Preview
@Composable
fun PhoneLoginPageTest() {
}
