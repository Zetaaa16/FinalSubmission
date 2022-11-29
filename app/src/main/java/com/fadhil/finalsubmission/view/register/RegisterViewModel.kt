package com.fadhil.finalsubmission.view.register

import androidx.lifecycle.ViewModel
import com.fadhil.finalsubmission.data.StoryRepository


class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}