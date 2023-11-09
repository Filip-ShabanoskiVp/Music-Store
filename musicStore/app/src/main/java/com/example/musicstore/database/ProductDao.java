package com.example.musicstore.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.musicstore.models.Products;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Products products);

    @Delete
    void deleteProduct(Products products);

    @Update
    void updateProduct(Products products);

    @Query("select * from products")
    LiveData<List<Products>> getAllProducts();
}
