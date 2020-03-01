package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

import com.example.musicplayer.models.Song;

import java.util.ArrayList;

public class Main1Activity extends AppCompatActivity implements songDialog.songDialogListener {

    private Button titleButton;
    private Button removeAll;
    EditText editText;
    DatabaseHelper myDB;
    public ArrayList<String> songList = new  ArrayList<>();
    public ArrayAdapter listAdapter;
    public ListView listView;

    private static final String TAG =  "Main1Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //empty constructor
        Song song = new Song("hi", "lalala", "timestamp ");

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

//    public void updateListView()
//    {
//
//    }

    public void  openDialog()
    {
        songDialog songDialog = new songDialog();
        songDialog.show(getSupportFragmentManager(),"song dialog");
    }

    @Override
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
}
