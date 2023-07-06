package com.example.pixabay.repo.database.favourite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pixabay.model.Hit

@Database(entities = [Hit::class], version = 1, exportSchema = false)
abstract class FavouritePostsDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
    companion object{
        private lateinit var instance: FavouritePostsDatabase
        fun getInstance(context: Context): FavouritePostsDatabase {
            synchronized(FavouritePostsDatabase::class.java){
                if(!Companion::instance.isInitialized){
                    instance = Room.databaseBuilder(
                        context,
                        FavouritePostsDatabase::class.java,
                        "favourite_db"
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