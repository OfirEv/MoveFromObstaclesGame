package com.example.exe1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;


public class GameLostActivity extends AppCompatActivity {
    private AppCompatImageView imageView2;
    private MaterialButton tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_lost);
        findView();
        initView();
    }

    private void initView() {
        Intent previousIntent = getIntent();
        tryAgainButton.setOnClickListener(v ->changeActivity());
    }


    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findView() {
        imageView2 = findViewById(R.id.imageView2);
        tryAgainButton = findViewById(R.id.tryAgainButton);
    }
}