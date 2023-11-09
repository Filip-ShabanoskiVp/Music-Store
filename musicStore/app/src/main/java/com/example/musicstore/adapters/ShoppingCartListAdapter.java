package com.example.musicstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.R;
import com.example.musicstore.holders.ShoppingCartListHolder;
import com.example.musicstore.models.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartListAdapter extends RecyclerView.Adapter {
    List<ShoppingCart>dataset;
    Context context;
    ClickShoppingCartListener shoppingCartListener;
    public ShoppingCartListAdapter(Context context,ClickShoppingCartListener shoppingCartListener){
        this.dataset = new ArrayList<>();
        this.context = context;
        this.shoppingCartListener = shoppingCartListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_list,parent,false);
        return new ShoppingCartListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ShoppingCart shoppingCart = dataset.get(position);
        String name = shoppingCart.getProductName();
        String description = shoppingCart.getDescription();
        final String date = shoppingCart.getDate();
        float price = shoppingCart.getPrice();
        String image = shoppingCart.getImage();
        ((ShoppingCartListHolder)holder).setShoppingCartNameText(name);
        ((ShoppingCartListHolder)holder).setShoppingCartDescriptionText(description);
        ((ShoppingCartListHolder)holder).setShoppingCartDateText(date);
        ((ShoppingCartListHolder)holder).setShoppingCartPriceText(price);
        Picasso.with(context).load(image).into(((ShoppingCartListHolder)holder).getShoppingCartImageView());
        ((ShoppingCartListHolder)holder).getBtnCancelProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelProductFromShoppingCart(shoppingCart);
            }
        });
    }
    public interface ClickShoppingCartListener{
        void cancelProductFromShoppingCart(ShoppingCart shoppingCart);
    }
    public void cancelProductFromShoppingCart(ShoppingCart shoppingCart){
        shoppingCartListener.cancelProductFromShoppingCart(shoppingCart);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void updateShoppingCartDataset(List<ShoppingCart> newDataset){
        this.dataset = newDataset;
        notifyDataSetChanged();
    }
}
