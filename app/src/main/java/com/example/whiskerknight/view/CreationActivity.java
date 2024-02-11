package com.example.whiskerknight.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.whiskerknight.R;
import com.example.whiskerknight.model.Player;
import com.example.whiskerknight.viewmodel.PlayerVM;

public class CreationActivity extends AppCompatActivity {
    private Player player;
    private PlayerVM playerVM;
    private ImageView sprite;
    private ImageButton selectedImageButton;
    private RadioGroup radioGroupDifficulty;
    private EditText playerName;
    private Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_screen);

        playerVM = new ViewModelProvider(this).get(PlayerVM.class);
        player = new Player();

        sprite = findViewById(R.id.ibCharacter1);
        radioGroupDifficulty = findViewById(R.id.radioGroupDifficulty);
        playerName = findViewById(R.id.getPlayerName);
        btnContinue = findViewById(R.id.btnContinue);


        btnContinue.setEnabled(true);


        // Continue button click handler
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid(playerName.getText().toString())) {
                    int selectedRadioButtonId = radioGroupDifficulty.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedDifficulty = selectedRadioButton.getText().toString();

                    player.setDifficulty(selectedDifficulty);
                    player.setUsername(playerName.getText().toString().trim());
                    playerVM.difficultyModeConfiguration();

                    Intent intent = new Intent(CreationActivity.this, GameActivity.class);
                    startActivity(intent);
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
