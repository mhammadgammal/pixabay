package com.mhammad.photoapp.api;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts_table")
public class Hit {
    public Hit(String largeImageURL, int views, int likes, int comments, String user, String userImageURL) {
        this.largeImageURL = largeImageURL;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.user = user;
        this.userImageURL = userImageURL;
    }
@PrimaryKey(autoGenerate = true)
    private  int _ID;
    public int id;
    public String pageURL;
    public String type;
    public String tags;
    public String previewURL;
    public int previewWidth;
    public int previewHeight;
    public String webformatURL;
    public int webformatWidth;
    public int webformatHeight;
    public String largeImageURL;
    public int imageWidth;
    public int imageHeight;
    public int imageSize;
    public int views;
    public int downloads;
    public int collections;
    public int likes;
    public int comments;
    public int user_id;
    public String user;
    public String userImageURL;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }
}