package com.example.pixabay.repo.database.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixabay.model.FavouritePost
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouritePost(post: FavouritePost)

    @Query("select * from favourite_posts")
    fun getFavouritePosts(): Flow<List<FavouritePost>>

    @Query("delete from favourite_posts where post_id = :postId")
    fun deleteFavouritePost(postId: Int)
}