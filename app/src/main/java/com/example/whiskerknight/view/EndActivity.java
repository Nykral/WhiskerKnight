package com.example.whiskerknight.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whiskerknight.R;
import com.example.whiskerknight.Leaderboard.BuildLeaderboard;
import com.example.whiskerknight.Leaderboard.LeaderboardAdapter;
import com.example.whiskerknight.viewmodel.PlayerVM;

import pl.droidsonroids.gif.GifImageView;

public class EndActivity extends AppCompatActivity {
    private Button startOverButton;
    private PlayerVM playerVM;
    private BuildLeaderboard leaderboardList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        MediaPlayer music = MediaPlayer.create(EndActivity.this, R.raw.sad);
        music.start();
        music.setLooping(true);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        playerVM = new ViewModelProvider(this).get(PlayerVM.class);
        leaderboardList = BuildLeaderboard.getInstance();


        // displays player's current attempt
        TextView textViewCurrentAttempt = findViewById(R.id.textViewCurrentAttempt);
        textViewCurrentAttempt.setText(playerVM.playerEntry());

        // displays leaderboard
        leaderboardList.addEntry(playerVM.playerEntry());
        setLeaderboard();


        // checks for clicks
        startOverButton = findViewById(R.id.btnStartOver);
        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.stop();
                Intent intent = new Intent(EndActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setLeaderboard() {
        ListView listViewLeaderboard = findViewById(R.id.listViewLeaderboard);
        int leader1 = 0;
        int leader2 = 0;
        int leader3 = 0;

        LeaderboardAdapter<String> adapter = new LeaderboardAdapter(this,
                leaderboardList.sortList());
        listViewLeaderboard.setAdapter(adapter);
        leader1 += 1;
        leader2 += 2;
        leader3 += 3;
    }
}