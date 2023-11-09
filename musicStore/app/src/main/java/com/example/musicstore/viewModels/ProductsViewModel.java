package com.example.musicstore.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.musicstore.database.ProductRepository;
import com.example.musicstore.models.Products;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Products>> productList;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        productList = repository.getAllProducts();
    }
    public LiveData<List<Products>> getAllProducts(){
        return repository.getAllProducts();
    }

    public void insertProduct(Products products){
        repository.insertProduct(products);
    }
    public void updateProduct(Products products){
        repository.updateProduct(products);
    }
    public void deleteProduct(Products products){
        repository.deleteProduct(products);
    }
}
