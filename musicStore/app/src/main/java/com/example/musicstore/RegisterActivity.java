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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import com.example.musicstore.database.MusicStoreDatabase;
import com.example.musicstore.database.UsersDao;
import com.example.musicstore.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername,editTextPassword,editTextPasswordConfirm,editTextEmail;
    Button btnRegister;
    TextView textViewLogin;
    private UsersDao userDao;
    User user;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        userDao = Room.databaseBuilder(this, MusicStoreDatabase.class,"musicStore-database")
                .allowMainThreadQueries().build().userDao();
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordConfirm = editTextPasswordConfirm.getText().toString().trim();
                if (username.equals("")) {
                    editTextUsername.setError("Fill username field");
                }else if(email.equals("")){
                    editTextEmail.setError("Fill email field");
                }else if( password.equals("")){
                    editTextPassword.setError("Fill password field");
                }else if(passwordConfirm.equals("")){
                    editTextPasswordConfirm.setError("Fill passwordConfirm field");
                }
                else if(!email.matches("^[a-zA-Z0-9]+(\\.[_A-Za-z0-9-]+)*@[a-zA-Z]+(\\.[a-zA-Z]{2,})$")){
                    editTextEmail.setError("Wrong email format");
                }
                else if (password.equals(passwordConfirm)) {
                    User user = new User(username, password, email);
                    userDao.insert(user);
                    loadRegisterPage();
                    successfullyRegistered();
                } else {
                    editTextPasswordConfirm.setError("Password is not matching");
                }
            }
            });
    }
    public void successfullyRegistered(){
        Toast.makeText(this,"Successfully registered",Toast.LENGTH_LONG).show();
    }
    public void loadRegisterPage(){
        Intent registerActivity = new Intent(this,RegisterActivity.class);
        startActivity(registerActivity);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        user = (User) getIntent().getSerializableExtra("User");
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        String getSession = sessionMenagment.getSession();
        MenuItem itemLogIn = menu.findItem(R.id.menu_login);
        MenuItem itemLogOut = menu.findItem(R.id.menu_logout);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        MenuItem itemUserProfile = menu.findItem(R.id.menu_user_Profile);
        if(getSession!="" || acct!=null){
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