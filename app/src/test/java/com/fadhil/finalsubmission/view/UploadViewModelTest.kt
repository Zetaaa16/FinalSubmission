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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UploadViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var uploadViewModel: UploadViewModel

    private val dummyUploadSuccess = DataDummy.generateUploadSuccess()


    @Before
    fun setUp() {
        uploadViewModel = UploadViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when upload should not null and return result Success`() = runTest {
        val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val expectedUploadResponse = MutableLiveData<Result<UsualResponse>>()
        expectedUploadResponse.value = Result.Success(dummyUploadSuccess)
        Mockito.`when`(repository.uploadStories(imageMultipart, description)).thenReturn(
            expectedUploadResponse
        )
        val actualUploadResponse = uploadViewModel.uploadImage(imageMultipart, description).getOrAwaitValue {  }
        Mockito.verify(repository).uploadStories(imageMultipart, description)
        assertNotNull(actualUploadResponse)
        assertTrue(actualUploadResponse is Result.Success)
    }

    @Test
    fun `when upload failed return result error`() = runTest {

        val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val expectedUploadResponse = MutableLiveData<Result<UsualResponse>>()
        expectedUploadResponse.value = Result.Error("error")
        Mockito.`when`(repository.uploadStories(imageMultipart, description)).thenReturn(
            expectedUploadResponse
        )
        val actualUploadResponse = uploadViewModel.uploadImage(imageMultipart, description).getOrAwaitValue {  }
        Mockito.verify(repository).uploadStories(imageMultipart, description)
        assertTrue(actualUploadResponse is Result.Error)
    }


}