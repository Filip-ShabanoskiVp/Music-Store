package com.example.musicstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {

    @SerializedName("data")
    @Expose
    private List<ArtistData> data;

    public List<ArtistData> getData() {
        return data;
    }

    public void setData(List<ArtistData> data) {
        this.data = data;
    }
}
