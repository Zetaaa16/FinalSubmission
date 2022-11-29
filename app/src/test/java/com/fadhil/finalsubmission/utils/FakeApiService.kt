package com.fadhil.finalsubmission.utils

import com.fadhil.finalsubmission.data.GetStoryResponse
import com.fadhil.finalsubmission.data.GetStoryResult
import com.fadhil.finalsubmission.data.LoginResponse
import com.fadhil.finalsubmission.data.UsualResponse
import com.fadhil.finalsubmission.networking.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody


class FakeApiService : ApiService {

    override suspend fun register(name: String, email: String, password: String): UsualResponse {
        return com.fadhil.finalsubmission.utils.DataDummy.generateResponseRegister()
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        return com.fadhil.finalsubmission.utils.DataDummy.generateResponseLogin()
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
                createdAt = "2022-10-27T04:35:59.148Z",
                name = "nama $i",
                description = "desc $i",
                lon = 0.0,
                lat = 0.0
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