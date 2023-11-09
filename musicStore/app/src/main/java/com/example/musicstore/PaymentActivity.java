package com.example.musicstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.adapters.ShoppingCartListAdapter;
import com.example.musicstore.models.Products;
import com.example.musicstore.models.ShoppingCart;
import com.example.musicstore.models.User;
import com.example.musicstore.viewModels.ProductsViewModel;
import com.example.musicstore.viewModels.ShoppingCartViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity implements ShoppingCartListAdapter.ClickShoppingCartListener{
    User user;
    GoogleSignInClient mGoogleSignInClient;
    List<ShoppingCart>dataset;
    ShoppingCartListAdapter adapter;
    ShoppingCartViewModel shoppingCartViewModel;
    TextView totalPriceText;
    ProductsViewModel productsViewModel;
    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_2);
        btnPay = findViewById(R.id.btnPay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingCartListAdapter(this,this);
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
        totalPriceText = findViewById(R.id.textViewTotalPrice);
        shoppingCartViewModel.getAllShoppingCartProducts(getSession).observe(this, new Observer<List<ShoppingCart>>() {
            @Override
            public void onChanged(List<ShoppingCart> shoppingCarts) {
                float total = 0;
                for (int i = 0; i<shoppingCarts.size();i++){
                    if(getSession.equals(shoppingCarts.get(i).getUsername())){
                        if(shoppingCarts.size()>0){
                            total+=shoppingCarts.get(i).getPrice();
                            totalPriceText.setText(String.valueOf(total) + " Eur");
                            adapter.updateShoppingCartDataset(shoppingCarts);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount()>=1) {
                    byAllShoppingCartProducts();
                }else{
                    ThereIsNotProducts();
                }
            }
        });
    }
    public GoogleSignInAccount getUserWithGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        return acct;
    }
    public String getSessionActivity(){
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
        return getSession;
    }
    public void lunchPaymentActivityBack(){
        Intent intent = new Intent(this,PaymentActivity.class);
        startActivity(intent);
    }
    public void ThereIsNotProducts(){
        Toast.makeText(this,"There is no products in shopping cart",Toast.LENGTH_LONG).show();
    }
    public void SuccessfullyAllProductDeleted(String name){
        Toast.makeText(this,name + " All Products are successfully bought.\n You should wait 2 weeks to deliver all products.",
                Toast.LENGTH_LONG).show();
    }
    public void byAllShoppingCartProducts(){
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View view = getLayoutInflater().inflate(R.layout.by_all_shopping_cart_products,null);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();
            Button btnPay = view.findViewById(R.id.btnByProduct);
            final EditText editName = view.findViewById(R.id.editFullName);
            final EditText editCreditCart = view.findViewById(R.id.editCreditCart);
            final EditText editCountry = view.findViewById(R.id.editCountry);
            final EditText editEmbg = view.findViewById(R.id.editEmbg);
            TextView title = view.findViewById(R.id.textNewProduct);
            alertDialog.setCancelable(true);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editName.getText().toString().matches("") && !editCreditCart.getText().toString().matches("")
                            && !editCountry.getText().toString().matches("") && !editEmbg.getText().toString().matches("")){
                        shoppingCartViewModel.deleteAllProductsFromShoppingCart(getSession);
                        String fullName = editName.getText().toString().trim();
                        if(editEmbg.getText().toString().length()==13) {
                            if(editCreditCart.getText().toString().matches("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}")) {
                                SuccessfullyAllProductDeleted(fullName);
                                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                                alertDialog.dismiss();
                            }else{
                                editCreditCart.setError("Wrong credit cart format. Example(2568-2145-2384-4523)");
                            }
                        }else{
                            editEmbg.setError("The embg length must be 13 digits");
                        }
                    }else {
                        if(editName.getText().toString().matches("")){
                            editName.setError("Fill Full field");
                        }
                        if(editCreditCart.getText().toString().matches("")){
                            editCreditCart.setError("Fill Credit cart field");
                        }
                        if(editCountry.getText().toString().matches("")){
                            editCountry.setError("Fill Country field");
                        }
                        if(editEmbg.getText().toString().matches("")){
                            editEmbg.setError("Fill embg field");
                        }
                    }
                }
            });
            alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        user = (User) getIntent().getSerializableExtra("User");
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        String getSession = sessionMenagment.getSession();
        MenuItem itemLogIn = menu.findItem(R.id.menu_login);
        MenuItem itemLogOut = menu.findItem(R.id.menu_logout);
        MenuItem itemUserProfile = menu.findItem(R.id.menu_user_Profile);
        if(getSession!=""|| getUserWithGoogle()!=null){
            itemLogIn.setVisible(false);
            itemUserProfile.setVisible(true);
            itemLogOut.setVisible(true);
        }else {
            itemLogIn.setVisible(true);
            itemUserProfile.setVisible(false);
            itemLogOut.setVisible(false);
        }
        String apiDataActivity = this.getClass().getSimpleName();
        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        if(apiDataActivity.equals("ApiDataActivity")){
            itemSearch.setVisible(true);
        }else {
            itemSearch.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_user_Profile:
                Intent profile = new Intent(this,UserProfileActivity.class);
                startActivity(profile);
                break;
            case R.id.menu_logout:
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                finish();
                            }
                        });
                SessionMenagment sessionMenagment = new SessionMenagment(this);
                sessionMenagment.removeSession();
                Intent MoveToLogin = new Intent(this,LoginActivity.class);
                MoveToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(MoveToLogin);
                break;
            case R.id.menu_home:
                Intent home = new Intent(this,MainActivity.class);
                startActivity(home);
                break;
            case R.id.menu_login:
                Intent login = new Intent(this,LoginActivity.class);
                startActivity(login);
                break;
            case R.id.menu_register:
                Intent register = new Intent(this,RegisterActivity.class);
                startActivity(register);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void cancelProductFromShoppingCart(ShoppingCart shoppingCart) {
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
        String productName = shoppingCart.getProductName();
        String description = shoppingCart.getDescription();
        String date = shoppingCart.getDate();
        String image = shoppingCart.getImage();
        float price = shoppingCart.getPrice();
        String username = getSession;
        Products products = new Products(productName,description,date,image,price);
        productsViewModel.insertProduct(products);
        shoppingCartViewModel.cancelFromShoppingCart(shoppingCart);
        if(adapter.getItemCount()==1){
            startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
        }
    }
}
