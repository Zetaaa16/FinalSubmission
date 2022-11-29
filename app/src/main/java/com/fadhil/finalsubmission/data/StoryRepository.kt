package com.fadhil.finalsubmission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import com.fadhil.finalsubmission.data.Result
import com.fadhil.finalsubmission.networking.ApiService
import com.fadhil.finalsubmission.storage.database.story.StoryDatabase
import com.fadhil.finalsubmission.storage.database.story.StoryEntity


class StoryRepository(
    private val apiService: ApiService,
    private val database: StoryDatabase
) {
    companion object {
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            database: StoryDatabase
        ): StoryRepository = instance ?: synchronized(this) {
            instance ?: StoryRepository(apiService, database)
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.login(email, password)
            emit(Result.Success(data))
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(
                        Result.Error(
                            "Cek Koneksi Internet"
                        )
                    )
                }
                is HttpException -> {
                    val gson = Gson()
                    val type = object : TypeToken<UsualResponse>() {}.type
                    val errorResponse: UsualResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    emit(
                        Result.Error(
                            errorResponse?.message ?: ""
                        )

                    )
                }
            }
        }

    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<UsualResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.register(name, email, password)
            emit(Result.Success(data))
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(
                        Result.Error(
                            "Cek Koneksi Internet"
                        )
                    )
                }
                is HttpException -> {
                    val gson = Gson()
                    val type = object : TypeToken<UsualResponse>() {}.type
                    val errorResponse: UsualResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    emit(
                        Result.Error(
                            errorResponse?.message ?: ""
                        )
                    )
                }
            }
        }

    }


    fun uploadStories(
        file: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Result<UsualResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.uploadStories(file, description)
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }


    fun allStories(): LiveData<PagingData<StoryEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    fun locationStory(): LiveData<Result<List<GetStoryResult>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.allStories(location = 1)
            val listStory = response.listStory
            emit(Result.Success(listStory))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }

    }


}