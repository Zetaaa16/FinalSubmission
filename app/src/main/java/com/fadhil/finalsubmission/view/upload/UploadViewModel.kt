package com.fadhil.finalsubmission.view.upload

import androidx.lifecycle.ViewModel
import com.fadhil.finalsubmission.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(private val repository: StoryRepository) : ViewModel() {

    fun uploadImage(file: MultipartBody.Part, description: RequestBody) =
        repository.uploadStories(file, description)
}