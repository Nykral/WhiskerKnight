package com.example.whiskerknight.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whiskerknight.R;
import com.example.whiskerknight.model.Player;
import com.example.whiskerknight.viewmodel.PlayerVM;

public class CreationActivity extends AppCompatActivity {
    private Player player;
    private PlayerVM playerVM;
    private ImageButton sprite1;
    private ImageButton sprite2;
    private ImageButton sprite3;
    private ImageButton selectedImageButton;
    private RadioGroup radioGroupDifficulty;
    private EditText playerName;
    private Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_screen);

        playerVM = new ViewModelProvider(this).get(PlayerVM.class);
        //player = Player.getPlayer();

        sprite1 = findViewById(R.id.ibCharacter1);
        sprite2 = findViewById(R.id.ibCharacter2);
        sprite3 = findViewById(R.id.ibCharacter3);
        radioGroupDifficulty = findViewById(R.id.radioGroupDifficulty);
        playerName = findViewById(R.id.getPlayerName);
        btnContinue = findViewById(R.id.btnContinue);


        btnContinue.setEnabled(false);


        // Monitor for button clicks
        sprite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicksCount = 0;
                player.setAvatar("sprite1");
                btnContinue.setEnabled(true);
                onImageButtonClick(sprite1);
                clicksCount += 1;
            }
        });

        sprite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicksCount = 0;
                player.setAvatar("sprite2");
                btnContinue.setEnabled(true);
                onImageButtonClick(sprite2);
                clicksCount++;
            }
        });

        sprite3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicksCount = 0;
                player.setAvatar("sprite3");
                btnContinue.setEnabled(true);
                onImageButtonClick(sprite3);
                clicksCount++;
            }
        });


        // Continue button click handler
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicksCount = 0;
                if (isInputValid(playerName.getText().toString())) {
                    int selectedRadioButtonId = radioGroupDifficulty.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedDifficulty = selectedRadioButton.getText().toString();

                    player.setDifficulty(selectedDifficulty);
                    player.setUsername(playerName.getText().toString().trim());
                    playerVM.difficultyModeConfiguration();

                    Intent intent = new Intent(CreationActivity.this, GameActivity.class);
                    startActivity(intent);
                    clicksCount++;
                }
            }
        });
    }

    // Prevent invalid names from being used
    public static boolean isInputValid(String input) {
        return input != null && !input.trim().isEmpty();
    }


    public void onImageButtonClick(ImageButton imageButton) {
        if (selectedImageButton != null) {
            selectedImageButton.setColorFilter(Color.TRANSPARENT);
        }
        imageButton.setColorFilter(R.color.purple_500);
        selectedImageButton = imageButton;
    }

}
