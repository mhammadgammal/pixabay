package com.example.pixabay.model

import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Hit(
    val collections: Int,
    val comments: Int, //# of comments of the photo
    val downloads: Int, //# of downloads of the photo
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "post_id")
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int, //# of likes of photoes that received from the API
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String, //user_name
    val userImageURL: String,
    val user_id: Int,
    val views: Int,// # of views
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    var isFavourite: Boolean
)