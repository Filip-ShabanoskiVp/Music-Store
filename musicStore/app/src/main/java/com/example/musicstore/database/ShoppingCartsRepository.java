package com.example.musicstore.database;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.example.musicstore.models.ShoppingCart;

import java.util.List;

public class ShoppingCartsRepository {

    private MusicStoreDatabase database;
    private ShoppingCartDao shoppingCartDao;
    private LiveData<List<ShoppingCart>> shoppingCartList;

    public ShoppingCartsRepository(Application application){
        database = Room.databaseBuilder(application,MusicStoreDatabase.class,"musicStore-database")
                .allowMainThreadQueries().build();
        //shoppingCartDao = database.shoppingCartDao();
        //shoppingCartList = shoppingCartDao.getAllShoppingCarts();
    }

    public LiveData<List<ShoppingCart>> getShoppingCarts(String username){
        return database.shoppingCartDao().getAllShoppingCarts(username);
    }

    public void insertProductToShoppingCart(final ShoppingCart shoppingCart){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.shoppingCartDao().insertProductToShoppingCart(shoppingCart);
                return null;
            }
        }.execute();
    }

    public void cancelProductFromShoppingCart(final ShoppingCart shoppingCart){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.shoppingCartDao().cancelProductFromShoppingCart(shoppingCart);
                return null;
            }
        }.execute();
    }

    public void deleteAllFromShoppingCart(final String username){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.shoppingCartDao().deleteAllProductFromShoppingCart(username);
                return null;
            }
        }.execute();
    }


}
