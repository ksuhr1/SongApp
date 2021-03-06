package com.example.musicplayer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.Song;
import com.example.musicplayer.util.Utility;

import java.util.ArrayList;

//  recycling view in the lists
//  only shows small amounts of view at a given time
//  as you scroll it recycles the views
public class SongsRecyclerAdapter extends RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder> {

    private static final String TAG = "SongsRecyclerAdapter";

    private ArrayList<Song> mSongs =  new ArrayList<>();
    private OnSongListener mOnSongListener;

    public SongsRecyclerAdapter(ArrayList<Song> songs, OnSongListener onSongListener) {
        this.mSongs = songs;
        this.mOnSongListener = onSongListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_list_item, parent, false);
        return new ViewHolder(view, mOnSongListener);
    }

    // called for every single  entry in the list
    // set attributes to ViewHolder object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get entry in mSongs list

        try{
            String month = mSongs.get(position).getTimestamp().substring(0, 2);
            month = Utility.getMonthFromNumber(month);
            String year = mSongs.get(position).getTimestamp().substring(3);
            String timestamp = month + " " +  year;
            holder.timestamp.setText(timestamp);
            holder.title.setText(mSongs.get(position).getTitle());


        }catch(NullPointerException e){
            Log.e(TAG, "onBindViewHolder: NullPointerException"+ e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    //need a view holder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView title, timestamp;
        OnSongListener onSongListener;


        public ViewHolder(@NonNull View itemView, OnSongListener  onSongListener)
        {
            super(itemView);
            title = itemView.findViewById(R.id.song_title);
            timestamp = itemView.findViewById(R.id.song_timestamp);
            this.onSongListener =  onSongListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSongListener.onSongClick(getAdapterPosition());

        }
    }

    public interface OnSongListener  {
        void onSongClick(int position);


    }


}
