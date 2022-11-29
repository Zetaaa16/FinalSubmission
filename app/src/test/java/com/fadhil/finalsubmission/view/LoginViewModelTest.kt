package com.fadhil.finalsubmission.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadhil.finalsubmission.data.LoginResponse
import com.fadhil.finalsubmission.data.StoryRepository
import com.fadhil.finalsubmission.utils.DataDummy
import com.fadhil.finalsubmission.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import com.fadhil.finalsubmission.data.Result
import com.fadhil.finalsubmission.utils.getOrAwaitValue
import com.fadhil.finalsubmission.view.login.LoginViewModel
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateResponseLogin()
    private val dummyEmail = "faisal@mail.com"
    private val dummyPassword = "password"


    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when login success return Result Success`() = runTest {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Success(dummyLoginResponse)

        Mockito.`when`(repository.login(dummyEmail, dummyPassword)).thenReturn(
            expectedLogin
        )
        val actualRegister = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue {}
        Mockito.verify(repository).login(dummyEmail, dummyPassword)
        assertTrue(actualRegister is Result.Success)

    }

    @Test
    fun `when login failed return Result Error`() = runTest {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Error("error")

        Mockito.`when`(repository.login(dummyEmail, dummyPassword)).thenReturn(
            expectedLogin
        )
        val actualRegister = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue {}
        Mockito.verify(repository).login(dummyEmail, dummyPassword)
        assertTrue(actualRegister is Result.Error)

    }

}