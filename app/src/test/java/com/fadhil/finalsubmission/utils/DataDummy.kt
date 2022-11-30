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
                i.toString(),
                "https://story-api.dicoding.dev/images/stories/photos-1669810202247_0Rk3KCKE.jpg",
                "2022-11-30T16:26:17.148Z",
                "nama $i",
                "desc $i",
                1.2,
                3.4
            )
            item.add(story)
        }
        return item
    }

    fun generateDummyLocationResponse(): GetStoryResponse {
        val item: MutableList<GetStoryResult> = arrayListOf()
        for (i in 0..100) {
            val story = GetStoryResult(

                "https://story-api.dicoding.dev/images/stories/photos-1666845359145_osWD6l_I.jpg",
                "2022-10-27T04:35:59.148Z",
                "nama $i",
                "desc $i",

                0.0,
                i.toString(),
                0.0
            )
            item.add(story)
        }
        return GetStoryResponse(
            item,false,"Stories fetched successfully"
        )
    }

    fun generateResponseRegister(): UsualResponse {
        return UsualResponse(false, "success"
        )
    }


    fun generateUploadSuccess(): UsualResponse {
        return UsualResponse(false, "success")
    }

    fun generateResponseLogin(): LoginResponse {
        val loginResult = LoginResult(
            "forexample",
            "user-SjJ0bu9Gd4c8V42b",
            "102u234rh148324"
        )

        return LoginResponse(loginResult, false, "success")
    }
}