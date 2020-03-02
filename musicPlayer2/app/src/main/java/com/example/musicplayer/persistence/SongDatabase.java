package com.example.musicplayer.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.musicplayer.models.Song;

@Database(entities = {Song.class}, version=1)
public abstract class SongDatabase extends RoomDatabase {
    public static final String DATABASE_NAME  = "songs_db";

    private static SongDatabase instance;

    static SongDatabase getInstance(final Context context)
    {
        if(instance ==  null)
        {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    SongDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

}
