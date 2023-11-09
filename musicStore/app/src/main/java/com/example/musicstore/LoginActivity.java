package com.example.musicstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.musicstore.database.MusicStoreDatabase;
import com.example.musicstore.database.UsersDao;
import com.example.musicstore.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView textViewHomePage;
    EditText editUsername,editPassword;
    MusicStoreDatabase database;
    UsersDao userDao;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    int RC_SIGN_IN=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnProductsView();
        productListener();
        textViewHomePage = findViewById(R.id.textHomePage);
        textViewHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchHomePage();
            }
        });
        editUsername = findViewById(R.id.textUsername);
        editPassword = findViewById(R.id.textPassword);
        database = Room.databaseBuilder(this,MusicStoreDatabase.class,"musicStore-database")
                .allowMainThreadQueries()
                .build();
        userDao = database.userDao();
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(this,UserProfileActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    void lunchHomePage(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    void btnProductsView(){
        btnLogin = findViewById(R.id.btnLogin);
    }
    void lunchActivity() {
        String username = editUsername.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();
        User user = userDao.getUser(username,password);
        if(username.equals("")){
            editUsername.setError("Fill username field");
        }
        else if(password.equals("")){
            editPassword.setError("Fill password field");
        }
        else if(user!=null){
            SessionMenagment sessionMenagment = new SessionMenagment(this);
            sessionMenagment.SaveSession(user);
            Intent productActivity = new Intent(this,UserProfileActivity.class);
            //productActivity.putExtra("User",user);
            productActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(productActivity);
            //finish();
        }else {
            Toast.makeText(this,"Wrong password or username",Toast.LENGTH_LONG).show();
        }
    }
    void productListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchActivity();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        SessionMenagment sessionMenagment = new SessionMenagment(this);
        String userId = sessionMenagment.getSession();
        if(userId!=""){
            Intent productActivity = new Intent(this,UserProfileActivity.class);
            productActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(productActivity);
        }else {

        }
    }
}
