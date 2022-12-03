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
    private val dummyEmail = "forexample@mail.com"
    private val dummyPassword = "123456"


    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when login  not null and return Success`() = runTest {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Success(dummyLoginResponse)

        Mockito.`when`(repository.login(dummyEmail, dummyPassword)).thenReturn(
            expectedLogin
        )
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue {}
        Mockito.verify(repository).login(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Success)

    }

    @Test
    fun `when login failed and return Result Error`() = runTest {
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Error("error")

        Mockito.`when`(repository.login(dummyEmail, dummyPassword)).thenReturn(
            expectedLogin
        )
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue {}
        Mockito.verify(repository).login(dummyEmail, dummyPassword)

        assertTrue(actualLogin is Result.Error)

    }

}