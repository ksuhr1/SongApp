package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.musicplayer.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
//public class Main1Activity extends AppCompatActivity implements songDialog.songDialogListener
public class Main1Activity extends AppCompatActivity implements SongsRecyclerAdapter.OnSongListener, songDialog.songDialogListener, View.OnClickListener {

    private Button titleButton;
    private Button removeAll;
    EditText editText;
    DatabaseHelper myDB;
    public ArrayList<String> songList = new  ArrayList<>();
    public ArrayAdapter listAdapter;
    public ListView listView;

    private static final String TAG =  "Main1Activity";


    // UI components
    private RecyclerView mRecyclerView;


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
//        setContentView(R.layout.activity_main1);
        setContentView(R.layout.activity_songs_list);

        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        initRecyclerView();
        insertFakeSongs();

        setSupportActionBar((Toolbar)findViewById(R.id.songs_toolbar));
        setTitle("Song list");

//        //empty constructor
//        Song song = new Song("hi", "lalala", "timestamp ");

//        listView = (ListView) findViewById(R.id.songList);
//        myDB =  new DatabaseHelper(this);
//        editText  = (EditText) findViewById(R.id.editText);
//        Cursor data = myDB.getListContents(); // cursor gets all contents of database
//
//
//        //populate array list with that data
//        if(data.getCount() == 0 )
//        {
//            Toast.makeText(Main1Activity.this, "The database was empty", Toast.LENGTH_SHORT);
//        }
//        else {
//            while(data.moveToNext())
//            {
//                songList.add(data.getString(1));
//                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
//                listView.setAdapter(listAdapter);
//            }
//
//        }
//
//        removeAll = (Button) findViewById(R.id.removeAll);
//        removeAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                songList.clear();
//                myDB.deleteAll();
//                listAdapter.notifyDataSetChanged();
//            }
//        });
//
//        //initalize button
//        titleButton = (Button) findViewById(R.id.addTitle);
//        titleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialog();
//            }
//        });
    }

    private void deleteSong(Song song)
    {
        mSongs.remove(song);
        mSongRecyclerAdapter.notifyDataSetChanged();
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

    private void  insertFakeSongs()
    {
        for(int i  = 0; i <  1000; i++)
        {
            Song song = new  Song();
            song.setTitle("title # " + i);
            song.setContent("content # " + i);
            song.setTimestamp("Jan 2020");
            mSongs.add(song);
        }
        mSongRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView); // connect  itemTouchHelper
        mSongRecyclerAdapter =  new SongsRecyclerAdapter(mSongs, this);
        mRecyclerView.setAdapter(mSongRecyclerAdapter);
    }

    public void  openDialog()
    {
        songDialog songDialog = new songDialog();
        songDialog.show(getSupportFragmentManager(),"song dialog");
    }

    public void applyText(String song) {
        Log.d("applyText", "song is "+ song);
        if(song.length() != 0)
        {
            AddData(song);
        }
        else
        {
            Toast.makeText(Main1Activity.this, "You must add song title", Toast.LENGTH_SHORT).show();
        }

    }


    public void AddData(String input)
    {
        Log.d("addData", input);
        boolean insertData = myDB.addData(input);
        if(insertData)
        {
            Toast.makeText(Main1Activity.this, "Successfully added a song!", Toast.LENGTH_SHORT).show();
            songList.add(input);
            Log.d("addData", "songList"+songList);

            listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
            listView.setAdapter(listAdapter);
            if(songList.size() == 1)
            {
                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
            else
            {
                listAdapter.notifyDataSetChanged();
            }

            Intent intent =  new Intent(Main1Activity.this, Main2Activity.class);
            startActivity(intent);
            // update list view with new song

        }
        else
        {
            Toast.makeText(Main1Activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSongClick(int position) {
        Log.d("onSongClick", "onSongClick: clicked"  + position);
        // if  you want to pass selected item to new activity attach to bundle
//        intent.putExtra("some object", mSongs.get(position));
//        mSongs.get(position);
        Intent intent  = new Intent(this,  SongActivity.class);
        intent.putExtra("selected_song",  mSongs.get(position));
        startActivity(intent);

        // custom classesm ust  be made parcelable  to be attached  to  bundles
        // should not attach very large dataseets to bundles.  no more than 50 entries
    }
}
