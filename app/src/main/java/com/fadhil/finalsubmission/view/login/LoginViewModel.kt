package com.fadhil.finalsubmission.view.login

import androidx.lifecycle.ViewModel
import com.fadhil.finalsubmission.data.StoryRepository


class LoginViewModel(private val repository: StoryRepository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)
}