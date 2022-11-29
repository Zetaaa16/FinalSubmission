package com.fadhil.finalsubmission.data

import android.content.Context
import com.fadhil.finalsubmission.networking.ApiConfig
import com.fadhil.finalsubmission.storage.database.story.StoryDatabase


object Injection {

    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository.getInstance(apiService, database)
    }
}