package com.ldl.ouc_iot.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserPage(userViewModel: UserViewModel) {
    val uiState by userViewModel.uiState.collectAsState()



    Column {
        Text(uiState.phone)

        Spacer(modifier = Modifier.height(20.dp))

        Text(uiState.username)


    }


}