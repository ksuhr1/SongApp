package com.example.musicplayer.async;

import android.os.AsyncTask;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.persistence.SongDAO;

public class InsertAsyncTask extends AsyncTask<Song, Void, Void> {
    private static final String TAG = "InsertAsyncTask";

    private SongDAO mSongDao;
    public InsertAsyncTask(SongDAO dao) {
        mSongDao = dao;

    }

    @Override
    protected Void doInBackground(Song... songs) {
        mSongDao.insertSongs(songs);
        return null;
    }
}
