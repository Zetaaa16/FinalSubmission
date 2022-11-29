package com.fadhil.finalsubmission.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadhil.finalsubmission.data.StoryRepository
import com.fadhil.finalsubmission.storage.database.story.StoryEntity


class MainViewModel(private val repository: StoryRepository) : ViewModel() {
    val listStoryResponse: LiveData<PagingData<StoryEntity>> =
        repository.allStories().cachedIn(viewModelScope)
}