package com.example.pixabay.repo.database.favourite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pixabay.model.FavouritePost
import com.example.pixabay.repo.database.cache.CachedPostsDatabase

@Database(entities = [FavouritePost::class], version = 1, exportSchema = false)
abstract class FavouriteDatabase: RoomDatabase() {
    abstract val favoriteDao: FavouriteDao

    companion object{
        private lateinit var instance: FavouriteDatabase
        fun getInstance(context: Context): FavouriteDatabase{
            synchronized(FavouriteDatabase::class.java) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context,
                        FavouriteDatabase::class.java,
                        "favourite_posts.db"
                        )
                        .fallbackToDestructiveMigrationOnDowngrade()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}