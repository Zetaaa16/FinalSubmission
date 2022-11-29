package com.fadhil.finalsubmission.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fadhil.finalsubmission.data.Injection
import com.fadhil.finalsubmission.data.StoryRepository
import com.fadhil.finalsubmission.view.login.LoginViewModel
import com.fadhil.finalsubmission.view.main.MainViewModel
import com.fadhil.finalsubmission.view.maps.MapsViewModel
import com.fadhil.finalsubmission.view.register.RegisterViewModel
import com.fadhil.finalsubmission.view.upload.UploadViewModel


class ViewModelFactory private constructor(
    private val repository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}