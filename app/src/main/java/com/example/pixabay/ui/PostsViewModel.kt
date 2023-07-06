package com.example.pixabay.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabay.cache.Resource
import com.example.pixabay.model.FavouritePost
import com.example.pixabay.model.Hit
import com.example.pixabay.repo.CachedPostsRepo
import com.example.pixabay.repo.FavouritePostsRepo
import com.example.pixabay.repo.PostRepo
import com.example.pixabay.repo.database.cache.CachedPostsDatabase
import com.example.pixabay.repo.database.favourite.FavouriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(private val application: Application) : ViewModel() {

    private var _postsMutableFlow: MutableStateFlow<List<Hit>> = MutableStateFlow(listOf())
    private var _favouritePostsMutableFlow: MutableStateFlow<List<FavouritePost>> = MutableStateFlow(listOf())
    private var _resourceMutableFlow: MutableStateFlow<Resource> =
        MutableStateFlow(Resource.SUCCESS(listOf()))
    private val cachedPostsDatabase = CachedPostsDatabase.getInstance(application)
    private val cachedPostsRepo = CachedPostsRepo(cachedPostsDatabase, application)
    private val favouritePostsDatabase = FavouriteDatabase.getInstance(application)
    private val favouritePostsRepo = FavouritePostsRepo(favouritePostsDatabase)

    private val postsRepo = PostRepo()
    val favouritePostsStateFlow: StateFlow<List<FavouritePost>>
        get() = _favouritePostsMutableFlow
    val resourceStateFlow: StateFlow<Resource>
        get() = _resourceMutableFlow
    init {
        getPosts()
    }


    private fun getPosts() {
        viewModelScope.launch {
            cachedPostsRepo.getCachedPosts().collect {
                withContext(Dispatchers.Main) { _resourceMutableFlow.value = it }
            }
        }
    }

    fun getPostsByTags(tag: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postsRepo.getPostsByTags(tag).collect {
                    _postsMutableFlow.value = it
                }
            }
        }
    }

    fun insertFavouritePost(post: FavouritePost, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePostsRepo.insertFavouritePost(post)
            }
        }
    }

    fun getFavouritePost() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePostsRepo.getFavouritePosts().collect{
                    withContext(Dispatchers.Main){
                        _favouritePostsMutableFlow.value = it
                    }
                }
            }
        }

    }

    fun deleteFavouritePost(postId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePostsRepo.deleteFavouritePost(postId)
            }
        }
    }

    companion object {
        private const val TAG = "PostsViewModel"
    }

}