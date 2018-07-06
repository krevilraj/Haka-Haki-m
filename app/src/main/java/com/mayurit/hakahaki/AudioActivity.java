package com.mayurit.hakahaki;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurit.hakahaki.Adapters.ProjectListAdapter;
import com.mayurit.hakahaki.Helpers.Constant;
import com.mayurit.hakahaki.Helpers.RecyclerItemClickListener;
import com.mayurit.hakahaki.Helpers.RetrofitAPI;
import com.mayurit.hakahaki.Model.AudioModel;
import com.mayurit.hakahaki.Model.ProjectModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioActivity extends AppCompatActivity {

    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";

    String post_id;
    ImageButton btnplay;
    private MediaPlayer mediaPlayer;
    private boolean playPause;
    TextView audio_title, audio_description, audio_date;
    private boolean initialStage = true;
    AudioModel post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

       /* Intent intent = getIntent();
        post = (AudioModel) getIntent().getSerializableExtra(EXTRA_OBJC);
        Toast.makeText(this, "categ = " + post_id, Toast.LENGTH_SHORT).show();
        audio_title = findViewById(R.id.audio_title);
        audio_description = findViewById(R.id.audio_description);
        audio_date = findViewById(R.id.audio_date);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        btnplay = findViewById(R.id.btnplay);
      *//*  btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AudioActivity.this, "Play", Toast.LENGTH_SHORT).show();
                if (!playPause) {
                    Toast.makeText(AudioActivity.this, "Pause", Toast.LENGTH_SHORT).show();

                    if (initialStage) {
                        new Player().execute("https://www.ssaurel.com/tmp/mymusic.mp3");
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }

                    playPause = true;

                } else {
                    Toast.makeText(AudioActivity.this, "playing", Toast.LENGTH_SHORT).show();

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }

                    playPause = false;
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

*//*

        displayAudioDetail();
        fetchData();*/

    }

   /* public void fetchData() {

        Call<AudioModel> noticeList = RetrofitAPI.getService().getAudioDetail("audio",post.getID());
        noticeList.enqueue(new Callback<AudioModel>() {
            @Override
            public void onResponse(Call<AudioModel> call, Response<AudioModel> response) {
                post = (AudioModel) response.body();
                if (post != null) {
                    displayAudioDetail();

                } else {
                    // showNoItemView(true);
                }
            }


            @Override
            public void onFailure(Call<AudioModel> call, Throwable throwable) {
                Toast.makeText(AudioActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void displayAudioDetail(){
        audio_title.setText(post.getPostTitle());
        audio_date.setText(post.getPostDate());
        Toast.makeText(this, "test" +post.getPostContent(), Toast.LENGTH_SHORT).show();
        audio_description.setText(post.getPostContent());

    }*/
}
