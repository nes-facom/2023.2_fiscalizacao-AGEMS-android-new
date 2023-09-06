package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.model.UserResponse

interface AuthenticationRepository {

    suspend fun registerUser(user: User): Resource<UserResponse>

    suspend fun loginUser(user: User): Resource<UserResponse>

    suspend fun refreshToken(): Resource<UserResponse>
}
