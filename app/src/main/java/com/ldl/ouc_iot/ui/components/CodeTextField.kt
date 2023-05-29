package com.ldl.ouc_iot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CodeTimer(timerState: State<Nothing>, onClick: () -> Unit) {
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

    Row {

        if (timerState == State.Loading) {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                modifier = Modifier.size(22.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(20.dp))
        } else {
            Text(if (timerState is State.Success) {
                if (remainingTime > 0) stringResource(id = R.string.resendAfter, remainingTime)
                else stringResource(id = R.string.resend)
            } else stringResource(id = R.string.getCode),
                modifier = Modifier.clickable(timerState != State.Loading && remainingTime == 0) { onClick() })
            Spacer(modifier = Modifier.width(15.dp))
        }
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
        trailingIcon = { CodeTimer(codeState, onClick = getCode) },
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
