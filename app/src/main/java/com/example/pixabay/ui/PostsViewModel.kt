package com.example.pixabay.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabay.model.Hit
import com.example.pixabay.repo.CachedPostsRepo
import com.example.pixabay.repo.FavouritePostsRepo
import com.example.pixabay.repo.PostRepo
import com.example.pixabay.repo.database.favourite.FavouritePostsDatabase.Companion.getInstance
import com.example.pixabay.repo.database.favourite.cache.CachedPostsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(private val application: Application) : ViewModel() {

    private var _postsMutableFlow: MutableStateFlow<List<Hit>> = MutableStateFlow(listOf())
    private val favouritePostsDatabase = getInstance(application)
    private val cachedPostsDatabase = CachedPostsDatabase.getInstance(application)
    private val favouritePostsRepo = FavouritePostsRepo(favouritePostsDatabase)
    private val cachedPostsRepo = CachedPostsRepo(cachedPostsDatabase, application)
    private val postsRepo = PostRepo()
    val postsStateFlow: StateFlow<List<Hit>>
        get() = _postsMutableFlow
    init {
        getRemotePosts()
    }

    private fun getRemotePosts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
            postsRepo.getRemotePosts().collect{
                withContext(Dispatchers.Main){ _postsMutableFlow.value = it }
            }
            }
        }
    }


    fun getPostsByTags(tag: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postsRepo.getPostsByTags(tag).collect{
                    _postsMutableFlow.value = it
                }
            }
        }
    }

    fun insertFavouritePost(post: Hit, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePostsRepo.insertFavouritePost(post)
            }
        }
    }

    fun getFavouritePost(context: Context): List<Hit> {
        var favouritePosts: List<Hit>? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePosts = favouritePostsRepo.getFavouritePosts()
            }
        }
        return favouritePosts!!
    }

    fun deleteFavouritePost(postId: Int, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritePostsRepo.deleteFavouritePost(postId)
            }
        }
    }

    companion object {
        private const val TAG = "PostsViewModel"
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun cache() {
//        if (isNetworkConnected(application)){
//            viewModelScope.launch{
//                withContext(Dispatchers.IO) {
//                    val hits = postsRepo.getRemotePosts()
//                    cachedPostsDatabase.withTransaction {
//                        cachedPostsDatabase.cacheDao.deleteCachedPosts()
//                        cachedPostsDatabase.cacheDao.insertCachedPosts(hits)
//                    }
//                }}
//        }
//        else {
//            cachedPostsDatabase.cacheDao.getCachedPosts()
//        }
    }
}