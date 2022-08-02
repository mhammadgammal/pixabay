package com.mhammad.photoapp.favouritedatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mhammad.photoapp.api.Hit;

@Database(entities = {Hit.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract FavouriteDAO favouriteDAO();
    private static volatile DataBase INSTANCE;

    public static synchronized DataBase getDatabase(final Context context){
        if(INSTANCE == null){

                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "AttendanceDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
        }
        return INSTANCE;
    }
}

