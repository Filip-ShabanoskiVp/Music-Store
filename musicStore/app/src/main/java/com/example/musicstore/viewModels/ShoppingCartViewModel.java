package com.example.musicstore.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.musicstore.database.ShoppingCartsRepository;
import com.example.musicstore.models.ShoppingCart;

import java.util.List;

public class ShoppingCartViewModel extends AndroidViewModel {
    private ShoppingCartsRepository repository;
    private LiveData<List<ShoppingCart>> shoppingCartList;
    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingCartsRepository(application);
        //shoppingCartList = repository.getShoppingCarts();
    }

    public LiveData<List<ShoppingCart>> getAllShoppingCartProducts(String username){
        return repository.getShoppingCarts(username);
    }
    public void insertToShoppingCart(ShoppingCart shoppingCart){
        repository.insertProductToShoppingCart(shoppingCart);
    }
    public void cancelFromShoppingCart(ShoppingCart shoppingCart){
        repository.cancelProductFromShoppingCart(shoppingCart);
    }
    public void deleteAllProductsFromShoppingCart(String username){
        repository.deleteAllFromShoppingCart(username);
    }
}
