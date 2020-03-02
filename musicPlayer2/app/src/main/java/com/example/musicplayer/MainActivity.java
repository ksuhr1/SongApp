package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

import com.example.musicplayer.adapters.SongsRecyclerAdapter;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.persistence.SongRepository;
import com.example.musicplayer.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//public class Main1Activity extends AppCompatActivity implements songDialog.songDialogListener
public class MainActivity extends AppCompatActivity implements SongsRecyclerAdapter.OnSongListener, View.OnClickListener {

    private static final String TAG =  "MainActivity";
    // UI components
    private RecyclerView mRecyclerView;

    // repository
    private SongRepository mSongRepository;

    //vars
    private ArrayList<Song> mSongs = new ArrayList<>();

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }

    private SongsRecyclerAdapter mSongRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        mRecyclerView = findViewById(R.id.recyclerView);
        mSongRepository = new SongRepository(this);
        setTitle("Song list");
        setSupportActionBar((Toolbar)findViewById(R.id.songs_toolbar));
        findViewById(R.id.fab).setOnClickListener(this);

        initRecyclerView();
        retrieveSongs();
    }

    // use observer to view changes to live data  object
    // like attaching a listener to the database
    // attaching observer to livedata object
    // if anything changed in databse, observe will cause onChanged method and
    // will rerquery notes
    private void retrieveSongs(){
        mSongRepository.retrieveSongsTask().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                if(mSongs.size() > 0){
                    mSongs.clear();
                }
                if(songs != null)
                {
                    mSongs.addAll(songs);
                }
                mSongRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteSong(Song song)
    {
        mSongs.remove(song);
        mSongRecyclerAdapter.notifyDataSetChanged();
        mSongRepository.deleteSong(song);

    }


    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
        // used to rearrange list items
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteSong(mSongs.get(viewHolder.getAdapterPosition()));
        }
    };


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView); // connect  itemTouchHelper
        mSongRecyclerAdapter =  new SongsRecyclerAdapter(mSongs, this);
        mRecyclerView.setAdapter(mSongRecyclerAdapter);
    }


    @Override
    public void onSongClick(int position) {
        Log.d("onSongClick", "onSongClick: clicked"  + position);
        Intent intent  = new Intent(this,  SongActivity.class);
        intent.putExtra("selected_song",  mSongs.get(position));
        startActivity(intent);
    }
}
