package com.fadhil.finalsubmission.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
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
import com.fadhil.finalsubmission.data.UsualResponse
import com.fadhil.finalsubmission.utils.getOrAwaitValue
import com.fadhil.finalsubmission.view.upload.UploadViewModel
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UploadViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var createStoryViewModel: UploadViewModel

    private val dummyUploadSuccess = DataDummy.generateUploadSuccess()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()

    @Before
    fun setUp() {
        createStoryViewModel = UploadViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when upload success return result Success`() = runTest {
        val expectedUploadResponse = MutableLiveData<Result<UsualResponse>>()
        expectedUploadResponse.value = Result.Success(dummyUploadSuccess)
        Mockito.`when`(repository.uploadStories(dummyMultipart, dummyDescription)).thenReturn(
            expectedUploadResponse
        )
        val actualUploadResponse = createStoryViewModel.uploadImage(dummyMultipart,dummyDescription).getOrAwaitValue {  }
        Mockito.verify(repository).uploadStories(dummyMultipart, dummyDescription)
        assertTrue(actualUploadResponse is Result.Success)
    }

    @Test
    fun `when upload failed return result error`() = runTest {
        val expectedUploadResponse = MutableLiveData<Result<UsualResponse>>()
        expectedUploadResponse.value = Result.Error("error")
        Mockito.`when`(repository.uploadStories(dummyMultipart, dummyDescription)).thenReturn(
            expectedUploadResponse
        )
        val actualUploadResponse = createStoryViewModel.uploadImage(dummyMultipart,dummyDescription).getOrAwaitValue {  }
        Mockito.verify(repository).uploadStories(dummyMultipart, dummyDescription)
        assertTrue(actualUploadResponse is Result.Error)
    }


}