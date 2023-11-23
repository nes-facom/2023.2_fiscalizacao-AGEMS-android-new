package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.commons.Resource
import com.ufms.nes.core.data.network.ApiService
import com.ufms.nes.features.Mocks.loginSuccessfulAuthToken
import com.ufms.nes.features.Mocks.loginSuccessfulRefreshToken
import com.ufms.nes.features.Mocks.registerSuccessfulAuthToken
import com.ufms.nes.features.Mocks.registerSuccessfulRefreshToken
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.UserDTO
import com.ufms.nes.features.authentication.data.model.UserResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthenticationRepositoryImplTest {

    @MockK
    private lateinit var apiService: ApiService
    private lateinit var authRepository: AuthenticationRepository
    private var localService: LocalService = mockk()

    private val userMock: UserDTO = mockk()

    @Before
    fun setup() {
        apiService = mockk()
        authRepository = AuthenticationRepositoryImpl(apiService, localService)
    }

    @Test
    fun loginUser_eventUnSuccessful() = runBlocking {
        coEvery { apiService.loginUser(userMock) } returns UserResponse(loginSuccessfulAuthToken, loginSuccessfulRefreshToken)
        coEvery { localService.saveBearerToken(any()) } just Runs
        coEvery { localService.saveRefreshToken(any()) } just Runs
        coEvery { localService.saveUserLogged(any()) } just Runs

        val result = authRepository.loginUser(userMock)

        assertTrue(result.data is UserResponse)
        assertEquals(loginSuccessfulAuthToken, result.data?.accessToken)
        assertEquals(loginSuccessfulRefreshToken, result.data?.refreshToken)
    }

    @Test
    fun loginUser_throwException() = runBlocking {
        coEvery { apiService.loginUser(userMock) } throws Exception("this is a test")
        coEvery { localService.saveBearerToken(any()) } just Runs
        coEvery { localService.saveRefreshToken(any()) } just Runs
        coEvery { localService.saveUserLogged(any()) } just Runs

        val result = authRepository.loginUser(userMock)

        assertEquals(ERROR_MESSAGE, result.error)
    }
    @Test
    fun registerUser_eventUnSuccessful() = runBlocking {
        coEvery { apiService.registerUser(userMock) } returns UserResponse(registerSuccessfulAuthToken, registerSuccessfulRefreshToken)
        coEvery { localService.saveBearerToken(any()) } just Runs
        coEvery { localService.saveRefreshToken(any()) } just Runs
        coEvery { localService.saveUserLogged(any()) } just Runs

        val result = authRepository.registerUser(userMock)

        assertTrue(result is Resource)
        assertTrue(result.data is UserResponse)
        assertTrue(result.data is UserResponse)
        assertEquals(registerSuccessfulAuthToken, result.data?.accessToken)
        assertEquals(registerSuccessfulRefreshToken, result.data?.refreshToken)
    }

    @Test
    fun registerUser_throwException() = runBlocking {
        coEvery { apiService.registerUser(userMock) } throws Exception("this is a test")
        coEvery { localService.saveBearerToken(any()) } just Runs
        coEvery { localService.saveRefreshToken(any()) } just Runs
        coEvery { localService.saveUserLogged(any()) } just Runs

        val result = authRepository.registerUser(userMock)

        assertEquals(ERROR_MESSAGE, result.error)
    }
}
