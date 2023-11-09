package com.example.musicstore.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.example.musicstore.R;

public class MusicPlayService extends Service {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.mytrack);
        mediaPlayer.setLooping(false);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}
