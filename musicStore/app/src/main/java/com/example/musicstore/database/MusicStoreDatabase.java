package com.example.musicstore.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.musicstore.models.Products;
import com.example.musicstore.models.ShoppingCart;
import com.example.musicstore.models.User;

@Database(entities = {User.class, Products.class, ShoppingCart.class},version = 1,exportSchema = false)
public abstract class MusicStoreDatabase extends RoomDatabase {
    public abstract UsersDao userDao();
    public abstract ProductDao productDao();
    public abstract ShoppingCartDao shoppingCartDao();
}
