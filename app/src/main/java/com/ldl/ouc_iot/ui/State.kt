package com.ldl.ouc_iot.ui

sealed interface State<out T> {

    object None : State<Nothing>
    object Loading : State<Nothing>
    class Success<R> : State<R>
    class Failed(val message: String) : State<Nothing>
}