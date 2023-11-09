package com.example.musicstore.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.R;

public class ShoppingCartListHolder extends RecyclerView.ViewHolder {

    private ImageView shoppingCartImageView;
    private TextView shoppingCartTextViewName;
    private TextView shoppingCartTextViewDescription;
    private TextView shoppingCartTextViewDate;
    private TextView shoppingCartTextViewPrice;
    private Button btnCancelProduct;

    public ShoppingCartListHolder(@NonNull View itemView) {
        super(itemView);
        shoppingCartImageView = itemView.findViewById(R.id.imageShoppingCartViewList);
        shoppingCartTextViewName = itemView.findViewById(R.id.textShoppingCartViewName);
        shoppingCartTextViewDescription = itemView.findViewById(R.id.textShoppingCartViewDescription);
        shoppingCartTextViewDate = itemView.findViewById(R.id.textShoppingCartViewDate);
        shoppingCartTextViewPrice = itemView.findViewById(R.id.textShoppingCartViewPrice);
        btnCancelProduct = itemView.findViewById(R.id.btnCancel);
    }
    public void setShoppingCartNameText(String text){
        shoppingCartTextViewName.setText("Product Name: "+ text);
    }
    public void setShoppingCartDescriptionText(String text){
        shoppingCartTextViewDescription.setText("Description: "+text);
    }
    public void setShoppingCartDateText(String text){
        shoppingCartTextViewDate.setText("Date: "+ text);
    }
    public void setShoppingCartPriceText(float price){
        shoppingCartTextViewPrice.setText("Cost: "+ String.valueOf(price) +"  Eur");
    }
    public ImageView getShoppingCartImageView(){
        return shoppingCartImageView;
    }
    public Button getBtnCancelProduct(){
        return btnCancelProduct;
    }
}
