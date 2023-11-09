package com.example.musicstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongAlbum {

    @SerializedName("title")
    @Expose
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
