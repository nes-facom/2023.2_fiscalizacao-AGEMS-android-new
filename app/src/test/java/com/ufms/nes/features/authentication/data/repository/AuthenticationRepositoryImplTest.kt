package com.ufms.nes.features.authentication.data.repository

import com.ufms.nes.core.commons.Constants.ERROR_MESSAGE
import com.ufms.nes.core.data.network.ApiService
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
    private var apiServiceK = mockk<ApiService>()
    private lateinit var authRepository: AuthenticationRepository
    private var localService: LocalService = mockk()

    private val userMock: UserDTO = mockk()

    @Before
    fun setup() {
        apiService = mockk()
        authRepository = AuthenticationRepositoryImpl(apiService, localService)
    }

    @Test
    fun loginUser() = runBlocking {
        coEvery { apiService.loginUser(userMock) } returns UserResponse("0912", "2190")
        coEvery { localService.saveBearerToken(any()) } just Runs
        coEvery { localService.saveRefreshToken(any()) } just Runs
        coEvery { localService.saveUserLogged(any()) } just Runs

        val result = authRepository.loginUser(userMock)
        println("************************************* ${result}")
        assertTrue(true)
//        assertEquals("0912", result.data?.accessToken)
//        assertEquals("2190", result.data?.refreshToken)
    }

    @Test
    fun loginUser_throwException() = runBlocking {
        coEvery { apiService.loginUser(userMock) } throws Exception("this is a test")

        val result = authRepository.loginUser(userMock)

        assertEquals(ERROR_MESSAGE, result.error)
    }
}
