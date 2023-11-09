package com.example.musicstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistData {

    @SerializedName("id")
    @Expose
    Long id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("duration")
    @Expose
    String duration;

    @SerializedName("artist")
    @Expose
    ArtistInfo artist;

    @SerializedName("album")
    @Expose
    SongAlbum album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArtistInfo getArtist() {
        return artist;
    }

    public void setArtist(ArtistInfo artist) {
        this.artist = artist;
    }

    public SongAlbum getAlbum() {
        return album;
    }

    public void setAlbum(SongAlbum album) {
        this.album = album;
    }
}
