package com.example.musicstore;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.musicstore.models.User;

public class SessionMenagment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String share_pref_name = "session";
    String SESSION_KEY="session_user";

    public SessionMenagment(Context context){
        sharedPreferences = context.getSharedPreferences(share_pref_name,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void SaveSession(User user){
        String username =user.getUsername();
        editor.putString("username",username).commit();
    }

    public String getSession(){
        return sharedPreferences.getString("username","");
    }
    public void removeSession(){
        editor.putString("username","").commit();
    }
}
