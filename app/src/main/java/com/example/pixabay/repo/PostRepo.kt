package com.example.pixabay.repo

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.room.withTransaction
import com.example.pixabay.cache.Resource
import com.example.pixabay.cache.networkBoundResource
import com.example.pixabay.model.FavouritePost
import com.example.pixabay.model.Hit
import com.example.pixabay.repo.api.PostsClint.retrofitService
import com.example.pixabay.repo.database.cache.CachedPostsDatabase
import com.example.pixabay.repo.database.favourite.FavouriteDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


open class PostRepo {
    open suspend fun getRemotePosts():Flow<List<Hit>> = flow{ val hits = retrofitService.getPosts().hits; emit(hits) }

    open suspend fun getPostsByTags(tag: String): Flow<List<Hit>> = flow {
        val taggedHits = retrofitService.getPostsByTag(tag).hits
        emit(taggedHits)
    }
    companion object {
        private const val TAG = "PostRepo"
    }
}


class FavouritePostsRepo(private val db: FavouriteDatabase) : PostRepo() {

    fun insertFavouritePost(post: FavouritePost) = db.favoriteDao.insertFavouritePost(post)

    fun getFavouritePosts() = db.favoriteDao.getFavouritePosts()

    fun deleteFavouritePost(postId: Int) = db.favoriteDao.deleteFavouritePost(postId)

}

class CachedPostsRepo(private val db: CachedPostsDatabase, private val context: Context) :
    PostRepo() {

    private suspend fun insertPosts(posts: List<Hit>) = db.cacheDao.insertCachedPosts(posts)

     fun getCachedPosts(): Flow<Resource> {
        Log.d(TAG, "getCachedPosts: fired")
        return networkBoundResource(
            query = { db.cacheDao.getCachedPosts() },
            fetch = {
                delay(2000)
                retrofitService.getPosts()
            },
            saveFetchResult = { posts ->
                db.withTransaction {
                    deleteCachedPosts()
                    insertPosts(posts)
                }
            },
            shouldFetch = isNetworkConnected(context = context)
        )
    }

    private suspend fun deleteCachedPosts() = db.cacheDao.deleteCachedPosts()

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        private const val TAG = "PostRepo"
    }
}