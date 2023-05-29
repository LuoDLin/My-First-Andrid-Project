package com.ldl.ouc_iot.ui.login

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.ldl.ouc_iot.R
import com.ldl.ouc_iot.ui.components.MyBaseTextField
import com.ldl.ouc_iot.ui.components.TextFieldState
import java.util.regex.Pattern


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    phoneState: PhoneTextFieldState,
) {
    MyBaseTextField(
        modifier = modifier,
        uiState = phoneState,
        label = stringResource(id = R.string.phone),
        maxLength = 11,
        filter = { it.isDigit() },
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Phone,
        keyboardActions = KeyboardActions(onNext = {
            phoneState.enableShowErrors()
            if (!phoneState.isError) defaultKeyboardAction(ImeAction.Next)
        })
    )
}


class PhoneTextFieldState : TextFieldState() {
    override fun validator(): Boolean {
        return Pattern.matches("1[3-9]\\d{9}", text)
    }

    override fun errorFor(): String {
        return if (text.isEmpty()) "输入号码为空"
        else "无效号码:$text"
    }
}