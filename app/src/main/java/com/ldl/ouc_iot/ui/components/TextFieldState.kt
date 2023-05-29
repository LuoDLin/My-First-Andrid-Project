package com.ldl.ouc_iot.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState() {
    var text: String by mutableStateOf("")

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)
    val isValid: Boolean
        get() = validator()
    val isError: Boolean
        get() = !isValid && displayErrors

    open fun validator(): Boolean = true
    open fun errorFor(): String = ""

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
        else enableShowErrors()
    }


    fun getError(): String? {
        return if (isError) {
            errorFor()
        } else {
            null
        }
    }

    fun enableShowErrors() {
        if (isFocusedDirty) displayErrors = true
    }
}