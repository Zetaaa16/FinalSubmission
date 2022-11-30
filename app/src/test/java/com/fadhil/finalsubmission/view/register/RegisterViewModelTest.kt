package com.fadhil.finalsubmission.view.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadhil.finalsubmission.data.StoryRepository
import com.fadhil.finalsubmission.data.UsualResponse
import com.fadhil.finalsubmission.utils.DataDummy
import com.fadhil.finalsubmission.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import com.fadhil.finalsubmission.data.Result
import com.fadhil.finalsubmission.utils.getOrAwaitValue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {
    private lateinit var registerViewModel: RegisterViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository

    private val dummyResponseRegister = DataDummy.generateResponseRegister()
    private val dummyName = "faisal"
    private val dummyEmail = "faisal@mail.com"
    private val dummyPassword = "password"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when register success return Result Success`() = runTest {
        val expectedRegister = MutableLiveData<Result<UsualResponse>>()
        expectedRegister.value = Result.Success(dummyResponseRegister)

        Mockito.`when`(repository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedRegister
        )
        val actualRegister = registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue {}
        Mockito.verify(repository).register(dummyName, dummyEmail, dummyPassword)
        assertTrue(actualRegister is Result.Success)

    }

    @Test
    fun `when register failed return Result Error`() = runTest {
        val expectedRegister = MutableLiveData<Result<UsualResponse>>()
        expectedRegister.value = Result.Error("failed")

        Mockito.`when`(repository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedRegister
        )
        val actualRegister = registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(repository).register(dummyName, dummyEmail, dummyPassword)
        assertTrue(actualRegister is Result.Error)
    }

}