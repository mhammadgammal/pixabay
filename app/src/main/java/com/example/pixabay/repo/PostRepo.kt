package com.example.pixabay.repo

import android.content.Context
import android.util.Log
import com.example.pixabay.model.Hit
import com.example.pixabay.repo.api.PostsClint.retrofitService
import com.example.pixabay.repo.database.favourite.FavouritePostsDatabase
import com.example.pixabay.repo.database.favourite.cache.CachedPostsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


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


class FavouritePostsRepo(private val db: FavouritePostsDatabase) : PostRepo() {

    suspend fun insertFavouritePost(post: Hit) = db.favoriteDao.insertFavouritePost(post)

    suspend fun getFavouritePosts() = db.favoriteDao.getFavouritePosts()

    suspend fun deleteFavouritePost(postId: Int) = db.favoriteDao.deleteFavouritePost(postId)

}

class CachedPostsRepo(private val db: CachedPostsDatabase, private val context: Context) :
    PostRepo() {

    suspend fun insertPosts(posts: List<Hit>) = db.cacheDao.insertCachedPosts(posts)

//    suspend fun getCachedPosts(): Flow<Resource> {
//        Log.d(TAG, "getCachedPosts: fired")
//        return networkBoundResource(
//            query = { db.cacheDao.getCachedPosts() },
//            fetch = {
//                delay(2000)
//                retrofitService.getPosts()
//            },
//            saveFetchResult = { posts ->
//                db.withTransaction {
//                    db.cacheDao.deleteCachedPosts()
//                    db.cacheDao.insertCachedPosts(posts)
//                }
//            },
//            shouldFetch = isNetworkConnected(context = context)
//        )
//    }

    suspend fun deleteCachedPosts() = db.cacheDao.deleteCachedPosts()

    companion object {
        private const val TAG = "PostRepo"
    }
}