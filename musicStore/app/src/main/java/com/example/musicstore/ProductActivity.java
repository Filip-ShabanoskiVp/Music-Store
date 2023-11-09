package com.example.musicstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.adapters.ListAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ListAdapter.ClickListener {
    ListAdapter adapter;
    List<Products> dataset;
    User user;
    FloatingActionButton floatingActionButton;
    GoogleSignInClient mGoogleSignInClient;
    ProductsViewModel productsViewModel;
    ShoppingCartViewModel shoppingCartViewModel;
    Context context;
    TextView textViewApiData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        initToolbar();
        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_1);
        floatingActionButton = findViewById(R.id.floatingActionButtonNewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter(this,this);
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
        productsViewModel.getAllProducts().observe(this,new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                if(products.size()>0){
                    adapter.updateDataset(products);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getSession.equals("") || getUserWithGoogle()!=null){
                    addNewProduct();
                }else{
                    UserIsNotLoged();
                    toLoginActivity();
                }
            }
        });
        textViewApiData = findViewById(R.id.textViewApiDataLink);
        textViewApiData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchApiDataActivity();
            }
        });
    }
    public void lunchApiDataActivity(){
        Intent intent = new Intent(this,ApiDataActivity.class);
        startActivity(intent);
    }
    public GoogleSignInAccount getUserWithGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        return acct;
    }
    public void toLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void addNewProduct(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.add_product,null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        Button btnAddProduct = view.findViewById(R.id.btnAddProduct);
        final EditText editName = view.findViewById(R.id.editName);
        final EditText editDescription = view.findViewById(R.id.editDescription);
        final EditText editDate = view.findViewById(R.id.editDate);
        final EditText editPrice = view.findViewById(R.id.editPrice);
        final EditText editImageLnk = view.findViewById(R.id.editImage);
        TextView title = view.findViewById(R.id.textByProducts);
        alertDialog.setCancelable(true);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editName.getText().toString().matches("") && !editDescription.getText().toString().matches("")
                        && !editDate.getText().toString().matches("") && !editPrice.getText().toString().matches("")
                        && !editImageLnk.getText().toString().matches("")){
                    String name = editName.getText().toString().trim();
                    String description = editDescription.getText().toString().trim();
                    String date = editDate.getText().toString().trim();
                    float price = Float.parseFloat(editPrice.getText().toString().trim());
                    String image = editImageLnk.getText().toString().trim();

                    if(date.matches("[0-3][0-9]/[0-1][0-9]/20[2-9][0-9]")) {
                        Products products = new Products(name, description, date, image, price);
                        productsViewModel.insertProduct(products);
                        alertDialog.dismiss();
                    }else {
                        editDate.setError("Wrong date format");
                    }
                }else {
                    if(editName.getText().toString().matches("")){
                        editName.setError("Fill Name field");
                    }
                    if(editDescription.getText().toString().matches("")){
                        editDescription.setError("Fill Description field");
                    }
                    if(editDate.getText().toString().matches("")){
                        editDate.setError("Fill Date field");
                    }
                    if(editPrice.getText().toString().matches("")){
                        editPrice.setError("Fill Price field");
                    }
                    if(editImageLnk.getText().toString().matches("")){
                        editImageLnk.setError("Fill Image field");
                    }
                }
            }
        });
        alertDialog.show();
    }
    public void UserIsNotLoged(){
        Toast.makeText(this,"User is not loged, please log in",Toast.LENGTH_LONG).show();
    }
    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
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
        if(getSession!="" || getUserWithGoogle()!=null){
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
    public void addToShoppingCartClicked(Products products) {
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        final String getSession = sessionMenagment.getSession();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(!getSession.equals("") || acct!=null){
            String productName = products.getName();
            String description = products.getDescription();
            String date = products.getDate();
            String image = products.getImage();
            float price = products.getPrice();
            String username = getSession;
            ShoppingCart shoppingCart = new ShoppingCart(productName,description,date,image,price,username);
            shoppingCartViewModel.insertToShoppingCart(shoppingCart);
            productsViewModel.deleteProduct(products);
            if(adapter.getItemCount()==1){
                startActivity(new Intent(getApplicationContext(),ProductActivity.class));
            }
        }else{
            UserIsNotLoged();
            toLoginActivity();
        }
    }
}
