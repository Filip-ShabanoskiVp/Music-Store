package com.example.musicstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistInfo {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("picture_small")
    @Expose
    String picture_small;

    public String getName() {
        return name;
    }

    public String getPicture_small() {
        return picture_small;
    }
}
