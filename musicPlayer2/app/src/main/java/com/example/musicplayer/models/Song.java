package com.example.musicplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private String title;
    private String content;
    private String timestamp;

    // default constructor
    public Song(String title, String content, String timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Song()
     {

    }

    protected Song(Parcel in) {
        title = in.readString();
        content = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    // getter methods
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }


    // setter methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(timestamp);
    }
}
