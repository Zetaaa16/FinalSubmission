package com.fadhil.finalsubmission.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fadhil.finalsubmission.data.GetStoryResult
import com.fadhil.finalsubmission.data.StoryRepository
import com.fadhil.finalsubmission.utils.DataDummy
import com.fadhil.finalsubmission.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import com.fadhil.finalsubmission.data.Result
import com.fadhil.finalsubmission.utils.getOrAwaitValue
import com.fadhil.finalsubmission.view.maps.MapsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStoryItem = DataDummy.generateDummyLocationResponse()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when get location story Should Not Null and Return Success`() = runTest {
        val expectedMaps = MutableLiveData<Result<List<GetStoryResult>>>()
        expectedMaps.value = Result.Success(dummyStoryItem.listStory)

        Mockito.`when`(repository.locationStory()).thenReturn(expectedMaps)
        val actualStory = mapsViewModel.getLocation().getOrAwaitValue()
        Mockito.verify(repository).locationStory()

        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)
        assertEquals(
            dummyStoryItem.listStory.size,
            (actualStory as Result.Success).data.size
        )
    }

    @Test
    fun `when get location story network error Should Return Error`() {
        val expectedMaps = MutableLiveData<Result<List<GetStoryResult>>>()
        expectedMaps.value = Result.Error("error")

        Mockito.`when`(repository.locationStory()).thenReturn(expectedMaps)
        val actualStory = mapsViewModel.getLocation().getOrAwaitValue()

        Mockito.verify(repository).locationStory()
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Error)
    }

}