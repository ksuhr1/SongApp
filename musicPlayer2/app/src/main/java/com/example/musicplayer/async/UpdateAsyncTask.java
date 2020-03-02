package com.example.musicplayer.async;

import android.os.AsyncTask;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.persistence.SongDAO;

public class UpdateAsyncTask extends AsyncTask<Song, Void, Void> {
    private static final String TAG = "UpdateAsyncTask";

    private SongDAO mSongDao;
    public UpdateAsyncTask(SongDAO dao) {
        mSongDao = dao;

    }

    @Override
    protected Void doInBackground(Song... songs) {
        mSongDao.update(songs);
        return null;
    }
}
