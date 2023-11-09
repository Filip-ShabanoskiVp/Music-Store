package com.example.musicstore.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.musicstore.models.ShoppingCart;

import java.util.List;

@Dao
public interface ShoppingCartDao {

    @Insert
    void insertProductToShoppingCart(ShoppingCart shoppingCart);

    @Delete
    void cancelProductFromShoppingCart(ShoppingCart shoppingCart);

    @Query("Delete from shoppingCarts where username = :username")
    void deleteAllProductFromShoppingCart(String username);

    @Query("Select * from shoppingCarts where username = :username")
    LiveData<List<ShoppingCart>> getAllShoppingCarts(String username);
}
