package com.example.pixabay.repo.database.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pixabay.model.Hit
import com.example.pixabay.repo.database.favourite.cache.CacheDao

@Database(entities = [Hit::class], version = 1, exportSchema = false)
abstract class CachedPostsDatabase
    : RoomDatabase() {
    abstract val cacheDao: CacheDao

    companion object {
        private lateinit var instance: CachedPostsDatabase

        fun getInstance(context: Context): CachedPostsDatabase {
            synchronized(CachedPostsDatabase::class.java) {
                if (!Companion::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context,
                        CachedPostsDatabase::class.java,
                        "cached_db"
                    )
                        .fallbackToDestructiveMigrationOnDowngrade()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}