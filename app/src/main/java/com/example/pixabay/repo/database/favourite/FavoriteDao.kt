package com.example.pixabay.repo.database.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixabay.model.Hit

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouritePost(post: Hit)

    @Query("select * from posts")
    suspend fun getFavouritePosts(): List<Hit>

    @Query("delete from posts where post_id = :postId")
    suspend fun deleteFavouritePost(postId: Int)

}