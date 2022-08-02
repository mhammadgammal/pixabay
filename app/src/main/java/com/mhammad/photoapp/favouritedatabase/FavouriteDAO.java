package com.mhammad.photoapp.favouritedatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mhammad.photoapp.api.Hit;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavouriteDAO {
    @Insert
    public Completable addPost(Hit post);
    @Delete
    public void deletePost(Hit post);
    @Query("select * from posts_table")
    public Single<List<Hit>> getAllPosts();
}
