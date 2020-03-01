package com.example.musicplayer;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        ListView listView = (ListView) findViewById(R.id.songList);
        myDB =  new DatabaseHelper(this);

        // need to populate array  list  from database

        ArrayList<String> songList = new  ArrayList<>();

        Cursor data = myDB.getListContents(); // cursor gets all contents of database

        //populate array list with that data
        if(data.getCount() == 0 )
        {
            Toast.makeText(ViewListContents.this, "The database was empty", Toast.LENGTH_SHORT);
        }
        else {
            while(data.moveToNext())
            {
                songList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
                listView.setAdapter(listAdapter);
            }

        }
    }
}
