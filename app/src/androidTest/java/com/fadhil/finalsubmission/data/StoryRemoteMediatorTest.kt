package com.fadhil.finalsubmission.data

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fadhil.finalsubmission.networking.ApiService
import com.fadhil.finalsubmission.storage.database.story.StoryDatabase
import com.fadhil.finalsubmission.storage.database.story.StoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediatorTest {
    private var mockApi: ApiService = FakeApiService()
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            mockDb,
            mockApi,
        )
        val pagingState = PagingState<Int, StoryEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {

    override suspend fun register(name: String, email: String, password: String): UsualResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun uploadStories(
        file: MultipartBody.Part,
        description: RequestBody
    ): UsualResponse {
        TODO("Not yet implemented")
    }

    override suspend fun allStories(page: Int, size: Int, location: Int): GetStoryResponse {
        val items: MutableList<GetStoryResult> = arrayListOf()
        for (i in 0..100) {
            val story = GetStoryResult(
                id = i.toString(),
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1666845359145_osWD6l_I.jpg",
                createdAt = "2022-11-30T16:23:18.152Z",
                name = "nama $i",
                description = "desc $i",
                lon = 1.2,
                lat = 3.4
            )
            items.add(story)
        }
        items.subList((page - 1) * size, (page - 1) * size + size)
        return GetStoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = items
        )
    }
}