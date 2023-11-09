package com.example.musicstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import com.example.musicstore.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserProfileActivity extends AppCompatActivity {
    private Button btnShoppingCart;
    private TextView textUsername;
    private User user;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        btnShoppingCartView();
        shoppingCartListener();
        user = (User) getIntent().getSerializableExtra("User");
        textUsername = findViewById(R.id.textViewShowUsername);
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        String getSession = sessionMenagment.getSession();

        if(getSession!=""){
            textUsername.setText("Username: "+getSession);
        }
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personId = acct.getId();
            textUsername.setText("Username: "+personName);
        }
    }
    public void btnShoppingCartView(){
        btnShoppingCart = findViewById(R.id.btnShoppingCart);
    }
    public void lunchShoppingCartActivity(){
        Intent shoppingCart = new Intent(this,PaymentActivity.class);
        startActivity(shoppingCart);
    }
    public void shoppingCartListener(){
        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchShoppingCartActivity();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        user = (User) getIntent().getSerializableExtra("User");
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        String getSession = sessionMenagment.getSession();
        MenuItem itemLogIn = menu.findItem(R.id.menu_login);
        MenuItem itemLogOut = menu.findItem(R.id.menu_logout);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        MenuItem itemUserProfile = menu.findItem(R.id.menu_user_Profile);
        if(getSession!=""||acct!=null){
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
}
