package com.example.musicplayer.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

import java.util.ArrayList;

//  recycling view in the lists
//  only shows small amounts of view at a given time
//  as you scroll it recycles the views
public class SongsRecyclerAdapter extends RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder> {

    private ArrayList<Song>

    //need a view holder class
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title, timestamp;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.song_title);

            timestamp = itemView.findViewById(R.id.song_timestamp);
        }

    }


}
