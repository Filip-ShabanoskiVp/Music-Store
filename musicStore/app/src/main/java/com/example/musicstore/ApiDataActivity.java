package com.example.musicstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicstore.adapters.ArtistAdapter;
import com.example.musicstore.adapters.ListAdapter;
import com.example.musicstore.apiClient.ApiClient;
import com.example.musicstore.models.Artist;
import com.example.musicstore.models.ArtistData;
import com.example.musicstore.models.User;
import com.example.musicstore.services.ArtistService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ApiDataActivity extends AppCompatActivity {
    User user;
    RecyclerView recyclerView;
//    String nameOfTheArtist="Tose Proeski";
    ArtistAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private List<ArtistData>artist = new ArrayList<>();

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_data);
        Toolbar toolbar = findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view_3);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArtistAdapter(artist,this);
//        LoadJson();
    }
    public void LoadJson(String nameOfTheArtist){
        ArtistService artistService = ApiClient.getRetrofit().create(ArtistService.class);

        Call<Artist>call;
        call = artistService.getArtists(nameOfTheArtist);

        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                if(response.isSuccessful() && response.body().getData()!=null){
                    if(!artist.isEmpty()){
                        artist.clear();
                    }
                    artist = response.body().getData();
                    adapter = new ArtistAdapter(artist,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.updateDataset(artist);
                }else{
                    Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

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
           SearchView searchView = (SearchView)menu.findItem(R.id.menu_search).getActionView();
           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   if(!query.equals(" ")){
                       LoadJson(query);
                   }
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {
                   LoadJson(newText);
                   return false;
               }
           });
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
