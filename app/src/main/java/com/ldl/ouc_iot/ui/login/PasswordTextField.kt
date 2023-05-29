package com.ldl.ouc_iot.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.ldl.ouc_iot.R
import com.ldl.ouc_iot.ui.components.MyBaseTextField
import com.ldl.ouc_iot.ui.components.TextFieldState

@OptIn(ExperimentalMaterial3Api::class) // OutlinedTextField is experimental in m3
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordState: PasswordTextFieldState,
    onImeAction: () -> Unit = {}
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }
    MyBaseTextField(
        modifier = modifier,
        uiState = passwordState,
        label = stringResource(id = R.string.password),
        maxLength = 16,
        trailingIcon = {
            Icon(imageVector = if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = "",
                modifier = Modifier.clickable { showPassword.value = !showPassword.value })
        },
        keyboardType = KeyboardType.Password,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = {
            passwordState.enableShowErrors()
            if (!passwordState.isError) onImeAction()
        }),
    )
}


class PasswordTextFieldState : TextFieldState() {
    override fun validator(): Boolean {
        return (text.length >= 6)
    }

    override fun errorFor(): String {
        return if (text.isEmpty()) "输入密码为空"
        else "密码长度小于6"
    }
}