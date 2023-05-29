package com.ldl.ouc_iot.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ldl.ouc_iot.R
import com.ldl.ouc_iot.ui.State
import com.ldl.ouc_iot.ui.components.MyBaseTextField
import com.ldl.ouc_iot.ui.components.TextFieldState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CodeTimer(timerState: State<Nothing>, getCode: () -> Unit) {
    var remainingTime by remember { mutableStateOf(0) }
    LaunchedEffect(timerState) {
        if (timerState is State.Success) {
            remainingTime = 60
            launch {
                while (remainingTime > 0) {
                    delay(1000)
                    remainingTime--
                }
            }
        }
    }

    if (timerState == State.Loading) {
        CircularProgressIndicator(
            strokeWidth = 3.dp,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(22.dp),
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        Text(if (timerState is State.Success) {
            if (remainingTime > 0) stringResource(id = R.string.resendAfter, remainingTime)
            else stringResource(id = R.string.resend)
        } else stringResource(id = R.string.getCode),
            modifier = Modifier
                .clickable(timerState != State.Loading || remainingTime == 0) { getCode() }
                .padding(end = 15.dp))
    }


}


/**
 * 验证码窗口
 */
@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    uiState: CodeTextFieldState,
    codeState: State<Nothing>,
    onImeAction: () -> Unit = {},
    getCode: () -> Unit = {}
) {

    MyBaseTextField(
        modifier = modifier,
        uiState = uiState,
        maxLength = 6,
        label = stringResource(id = R.string.code),
        trailingIcon = { CodeTimer(codeState, getCode = getCode) },
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = { onImeAction() })
    )
}

class CodeTextFieldState : TextFieldState() {

    override fun validator(): Boolean {
        return text.length == 6
    }

    override fun errorFor(): String {
        return if (text.isEmpty()) "验证码为空"
        else "验证码错误"
    }


}
