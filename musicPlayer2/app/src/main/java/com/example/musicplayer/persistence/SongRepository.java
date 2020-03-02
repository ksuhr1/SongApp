package com.example.musicplayer.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.musicplayer.async.DeleteAsyncTask;
import com.example.musicplayer.async.InsertAsyncTask;
import com.example.musicplayer.async.UpdateAsyncTask;
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
        new InsertAsyncTask(mSongDatabase.getSongDAO()).execute(song); //execute will start async task
    }

    public void updateSong(Song song)
    {
        new UpdateAsyncTask(mSongDatabase.getSongDAO()).execute(song);
    }

    public LiveData<List<Song>>retrieveSongsTask()
    {
        return mSongDatabase.getSongDAO().getSongs();
    }

    public void deleteSong(Song song)
    {
        new DeleteAsyncTask(mSongDatabase.getSongDAO()).execute(song);

    }

}
