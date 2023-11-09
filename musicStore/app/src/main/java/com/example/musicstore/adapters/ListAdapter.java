package com.example.musicstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.R;
import com.example.musicstore.holders.ListViewHolder;
import com.example.musicstore.models.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    List<Products> dataset;
    private Context context;
    private ClickListener clickListener;
    public ListAdapter(ClickListener clickListener,Context context){
        this.dataset = new ArrayList<>();
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final Products products = dataset.get(i);
        String name = products.getName();
        String description = products.getDescription();
        final String date = products.getDate();
        float price = products.getPrice();
        String image = products.getImage();
        ((ListViewHolder) holder).setNameText(name);
        ((ListViewHolder) holder).setDescriptionText(description);
        ((ListViewHolder) holder).setDateText(date);
        ((ListViewHolder) holder).setPriceText(price);
        Picasso.with(context).load(image).into(((ListViewHolder) holder).getImageView());
        ((ListViewHolder)holder).getBtnAddToShoppingCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToShoppingCart(products);
            }
        });
    }
    public interface ClickListener{
        void addToShoppingCartClicked(Products products);
    }
    public void addProductToShoppingCart(Products products){
        clickListener.addToShoppingCartClicked(products);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateDataset(List<Products> newDataset) {
        this.dataset = newDataset;
        notifyDataSetChanged();
    }
}