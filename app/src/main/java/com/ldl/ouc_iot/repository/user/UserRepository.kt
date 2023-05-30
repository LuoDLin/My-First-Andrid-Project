package com.ldl.ouc_iot.repository.user

import com.ldl.ouc_iot.datasource.local.entities.LocalUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    public val userInfo: Flow<LocalUser>

    suspend fun sync()
}