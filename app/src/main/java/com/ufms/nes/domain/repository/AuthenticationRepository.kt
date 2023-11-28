package com.ufms.nes.domain.repository

import com.ufms.nes.core.commons.Resource
import com.ufms.nes.data.network.model.UserDTO
import com.ufms.nes.data.network.model.response.UserResponse

interface AuthenticationRepository {

    suspend fun registerUser(user: UserDTO): Resource<UserResponse>

    suspend fun loginUser(user: UserDTO): Resource<UserResponse>

    suspend fun refreshToken(): Resource<UserResponse>
}
