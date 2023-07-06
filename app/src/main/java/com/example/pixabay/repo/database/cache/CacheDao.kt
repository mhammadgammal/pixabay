package com.example.pixabay.repo.database.favourite.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixabay.model.CachedPosts
import com.example.pixabay.model.Hit
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCachedPosts(post: List<Hit>)

    @Query("select * from posts")
    fun getCachedPosts(): Flow<List<Hit>>

    @Query("delete from posts")
    suspend fun deleteCachedPosts()
}