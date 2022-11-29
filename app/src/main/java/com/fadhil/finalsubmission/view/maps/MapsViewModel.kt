package com.fadhil.finalsubmission.view.maps

import androidx.lifecycle.ViewModel
import com.fadhil.finalsubmission.data.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getLocation() = repository.locationStory()
}