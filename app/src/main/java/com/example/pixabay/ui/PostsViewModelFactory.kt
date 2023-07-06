package com.example.pixabay.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostsViewModelFactory(val application: Application): ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostsViewModel::class.java)){
            return PostsViewModel(application) as T
        }
        throw java.lang.IllegalArgumentException("Invalid View Model")
    }
}