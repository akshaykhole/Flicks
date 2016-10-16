package com.akshaykhole.flicks.movies;
import android.os.Bundle;
import android.util.Log;

import com.akshaykhole.flicks.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieVideoPlayerActivity extends YouTubeBaseActivity {
    String API_KEY = "AIzaSyB94WBaagBrqy7rUY_9k-AdzmY5VOwEYaU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_video_player);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        String videoKey = getIntent().getStringExtra("videoKey");

                        youTubePlayer.cueVideo(videoKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("YOUTUBE", youTubeInitializationResult.toString());
                    }
                });
    }
}
