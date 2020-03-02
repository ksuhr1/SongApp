package com.example.musicplayer.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musicplayer.models.Song;

import java.util.List;

@Dao
public interface SongDAO {
    @Insert
    long[] insertSongs(Song... songs); // return long array that has all rows, if insert fails returns -1

    @Query("SELECT * FROM songs")
    LiveData<List<Song>> getSongs(); //livedata is data observation class

//    @Query("SELECT * FROM songs WHERE id= :id")
//    List<Song> getSongWithCustomQuery(String id);

    @Delete
    int delete(Song... songs);

    @Update
    int update(Song... songs); // returns integer that is hows many rows were affeected
}
