package com.fadhil.finalsubmission.utils

import com.fadhil.finalsubmission.data.*
import com.fadhil.finalsubmission.storage.database.story.StoryEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


object DataDummy {

    fun generateDummyStoryResponse(): List<StoryEntity> {
        val item: MutableList<StoryEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoryEntity(
                id = i.toString(),
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1666845359145_osWD6l_I.jpg",
                createdAt = "2022-10-27T04:35:59.148Z",
                name = "nama $i",
                description = "desc $i",
                lon = 0.0,
                lat = 0.0
            )
            item.add(story)
        }
        return item
    }

    fun generateDummyLocationResponse(): GetStoryResponse {
        val item: MutableList<GetStoryResult> = arrayListOf()
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
            item.add(story)
        }
        return GetStoryResponse(
            listStory = item,
            error = false,
            message = "Stories fetched successfully"
        )
    }

    fun generateResponseRegister(): UsualResponse {
        return UsualResponse(
            error = false, message = "success"
        )
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequestBody(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }

    fun generateUploadSuccess(): UsualResponse {
        return UsualResponse(
            error = false, message = "success"
        )
    }

    fun generateResponseLogin(): LoginResponse {
        val loginResult = LoginResult(
            userId = "user-WTdihB1x0Rl1zDRD",
            name = "faisal",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVdUZGloQjF4MFJsMXpEUkQiLCJpYXQiOjE2NjczMTQ3Njl9.qNWq-yGI2FVuvi8lixoRMkSe4xXp_7JrAzr1jkSfq9A"
        )

        return LoginResponse(
            loginResult = loginResult,
            error = false,
            message = "success"
        )
    }
}