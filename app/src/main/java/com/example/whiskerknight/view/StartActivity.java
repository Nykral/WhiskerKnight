package com.example.whiskerknight.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whiskerknight.R;


public class StartActivity extends AppCompatActivity {
    private Button btnStartGame;
    private Button btnExit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnExit = findViewById(R.id.btnExit);


        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, CreationActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                exitGame();
            }
        });
    }
    private void exitGame() {
        finishAffinity();
        System.exit(0);
    }
}