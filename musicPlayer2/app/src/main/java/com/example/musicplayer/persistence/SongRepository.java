package com.example.musicplayer.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.musicplayer.models.Song;

import java.util.List;

// methods that call DAO methods
public class SongRepository {
    private SongDatabase mSongDatabase;

    public SongRepository(Context context) {
        mSongDatabase = SongDatabase.getInstance(context); // reference using singleton pattern
    }

    public void insertSongTask(Song song)
    {
        
    }

    public void updateSong(Song song)
    {

    }

    public LiveData<List<Song>>retrieveSongsTask()
    {
        return null;
    }

    public void deleteSong(Song song)
    {

    }

}
