package com.example.musicstore.database;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.example.musicstore.models.Products;

import java.util.List;

public class ProductRepository {

    private MusicStoreDatabase database;
    private ProductDao productDao;
    private LiveData<List<Products>> productList;

   public ProductRepository(Application application){
       database = Room.databaseBuilder(application,MusicStoreDatabase.class,"musicStore-database")
               .allowMainThreadQueries()
               .build();
       productDao = database.productDao();
       productList = productDao.getAllProducts();
   }
   public LiveData<List<Products>> getAllProducts(){
       return database.productDao().getAllProducts();
   }

   public void insertProduct(final Products products){
       new AsyncTask<Void, Void, Void>() {
           @Override
           protected Void doInBackground(Void... voids) {
               database.productDao().insertProduct(products);
               return null;
           }
       }.execute();
   }

   public void updateProduct(final Products products){
       new AsyncTask<Void, Void, Void>() {
           @Override
           protected Void doInBackground(Void... voids) {
               database.productDao().updateProduct(products);
               return null;
           }
       }.execute();
   }

   public void deleteProduct(final Products products){
       new AsyncTask<Void, Void, Void>() {
           @Override
           protected Void doInBackground(Void... voids) {
               database.productDao().deleteProduct(products);
               return null;
           }
       }.execute();
   }
}
