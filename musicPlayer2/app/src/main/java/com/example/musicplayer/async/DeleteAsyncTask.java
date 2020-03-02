package com.example.musicplayer.async;

import android.os.AsyncTask;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.persistence.SongDAO;

public class DeleteAsyncTask extends AsyncTask<Song, Void, Void> {
    private static final String TAG = "DeleteAsyncTask";

    private SongDAO mSongDao;
    public DeleteAsyncTask(SongDAO dao) {
        mSongDao = dao;

    }

    @Override
    protected Void doInBackground(Song... songs) {
        mSongDao.delete(songs);
        return null;
    }
}
