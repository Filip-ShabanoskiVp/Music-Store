package com.example.musicstore.holders;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.R;

import java.util.Date;

public class ListViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewDescription;
    private TextView textViewDate;
    private TextView textViewPrice;
    private Button btnAddToShoppingCart;
    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewList);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewDate = itemView.findViewById(R.id.textViewDate);
        textViewPrice = itemView.findViewById(R.id.textViewPrice);
        btnAddToShoppingCart = itemView.findViewById(R.id.btnAddToShoppingCart);
    }

    public void setNameText(String text){
        textViewName.setText("Product Name: "+text);
    }
    public void setDescriptionText(String text){
        textViewDescription.setText("Description: "+text);
    }
    public void setDateText(String text){
        textViewDate.setText("Date: "+ text);
    }
    public void setPriceText(float price){
        textViewPrice.setText("Cost: "+String.valueOf(price) +"  Eur");
    }
    public ImageView getImageView(){
        return imageView;
    }
    public Button getBtnAddToShoppingCart(){
        return btnAddToShoppingCart;
    }
}