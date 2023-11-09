package com.example.musicstore.services;

import com.example.musicstore.models.Artist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistService {

    @GET("search?")
    Call<Artist> getArtists(
            @Query("q") String q);

}
