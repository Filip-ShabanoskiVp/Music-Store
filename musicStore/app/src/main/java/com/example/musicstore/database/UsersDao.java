package com.example.musicstore.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.musicstore.models.User;

@Dao
public interface UsersDao {

    @Query("SELECT * From User where username = :username and password = :password")
    User getUser(String username, String password);

    @Insert
    void insert(User user);
}