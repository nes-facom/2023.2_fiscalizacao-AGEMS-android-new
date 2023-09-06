package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.authentication.data.service.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticationRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var authRepository: AuthenticationRepository
    private var localService: LocalService = mockk()

    private val userMock: User = mockk()

    @Before
    fun setup() {
        apiService = mockk()
        authRepository = AuthenticationRepositoryImpl(apiService, localService)
    }

    @Test
    fun loginUser_eventUnSuccessful() = runBlocking {
        coEvery { apiService.loginUser(userMock) } returns UserResponse("0912", "2190")

        val result = authRepository.loginUser(userMock)

        assertTrue(result.data is UserResponse)
        assertEquals("0912", result.data?.accessToken)
        assertEquals("2190", result.data?.refreshToken)
    }

    @Test
    fun loginUser_throwException() = runBlocking {
        coEvery { apiService.loginUser(userMock) } throws Exception("this is a test")

        val result = authRepository.loginUser(userMock)

        assertEquals(ERROR_MESSAGE, result.error)
    }
}
