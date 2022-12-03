package com.fadhil.finalsubmission.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.fadhil.finalsubmission.networking.ApiService
import com.fadhil.finalsubmission.storage.database.story.StoryDatabase
import com.fadhil.finalsubmission.utils.DataDummy
import com.fadhil.finalsubmission.utils.MainDispatcherRule
import com.fadhil.finalsubmission.utils.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.fadhil.finalsubmission.storage.database.story.StoryEntity
import com.fadhil.finalsubmission.utils.getOrAwaitValue
import com.fadhil.finalsubmission.view.main.StoryAdapter
import com.fadhil.finalsubmission.view.StoryPagingSource
import com.fadhil.finalsubmission.view.noopListUpdateCallback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var storyDatabase: StoryDatabase

    @Mock
    private lateinit var StoryRepositoryMock: StoryRepository

    @Mock
    private lateinit var Storyrepository: StoryRepository


    private val dummyStoryItem = DataDummy.generateDummyLocationResponse()

    companion object{
        private const val dummyName = "forexample"
        private const val dummyPassword = "123456"
        private const val dummyEmail = "forexample@gmail.com"
    }

    @Before
    fun setUp() {
        Storyrepository = StoryRepository.getInstance(apiService, storyDatabase)
    }

    @Test
    fun `login test`() = runTest {
        val expectedLoginResponse = DataDummy.generateResponseLogin()
        Mockito.`when`(apiService.login(dummyEmail, dummyPassword)).thenReturn(expectedLoginResponse)
        val actualLogin = Storyrepository.login(dummyEmail, dummyPassword)
        actualLogin.observeForTesting {
            assertEquals((actualLogin.value as Result.Success).data.message, "success")
            assertFalse((actualLogin.value as Result.Success).data.error)
            assertEquals(expectedLoginResponse, (actualLogin.value as Result.Success).data)
        }
    }

    @Test
    fun `register test`() = runTest {
        val expectedRegisterResponse = DataDummy.generateResponseRegister()
        Mockito.`when`(apiService.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedRegisterResponse
        )
        val actualRegister = Storyrepository.register(dummyName, dummyEmail, dummyPassword)
        actualRegister.observeForTesting {
            assertEquals((actualRegister.value as Result.Success).data.message, "success")
            assertFalse((actualRegister.value as Result.Success).data.error)
        }

    }

    @Test
    fun `get Story with Paging test`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryResponse()
        val data: PagingData<StoryEntity> = StoryPagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<StoryEntity>>()
        expectedStory.value = data
        Mockito.`when`(StoryRepositoryMock.allStories()).thenReturn(expectedStory)
        val actualStory = StoryRepositoryMock.allStories().getOrAwaitValue { }
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.diffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)

    }

    @Test
    fun `get Story Location test`() = runTest {
        val expectedLocation = MutableLiveData<Result<List<GetStoryResult>>>()
        expectedLocation.value = Result.Success(dummyStoryItem.listStory)
        Mockito.`when`(StoryRepositoryMock.locationStory()).thenReturn(expectedLocation)
        val actualStoryLocation = StoryRepositoryMock.locationStory().getOrAwaitValue()
        assertNotNull(actualStoryLocation)
        assertTrue(actualStoryLocation is Result.Success)
        assertEquals(dummyStoryItem.listStory, (actualStoryLocation as Result.Success).data)
        assertEquals(
            dummyStoryItem.listStory.size,
            actualStoryLocation.data.size
        )
    }

    @Test
    fun `upload story test`() = runTest {
        val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )
        val expectedUpload = DataDummy.generateUploadSuccess()
        Mockito.`when`(
            apiService.uploadStories(
                imageMultipart, description
            )
        ).thenReturn(expectedUpload)
        val actualUpload = Storyrepository.uploadStories(imageMultipart, description)
        actualUpload.observeForTesting {
            assertFalse((actualUpload.value as Result.Success).data.error)
            assertEquals((actualUpload.value as Result.Success).data.message,
                expectedUpload.message
            )
        }

    }
}