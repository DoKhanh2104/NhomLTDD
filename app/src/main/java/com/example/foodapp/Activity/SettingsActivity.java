package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();

    }

    private void setVariable() {
        //Tro lai trang chu
        binding.backBtn.setOnClickListener(view -> finish());

        //Dang xuat
        binding.logout.setOnClickListener(view -> {
            Intent intent=new Intent(SettingsActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        //Chuyen sang trang danh muc yeu thich
        binding.fav.setOnClickListener(view -> {
            Intent intent=new Intent(SettingsActivity.this,LikeActivity.class);
            startActivity(intent);
        });

        //Chuyen sang trang danh muc theo doi don hang
        binding.ship.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, OrderActivity.class);
            startActivity(intent);
        });

        //Chuyen sang trang thong tin nhom
        Button profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, TeamInfoActivity.class);
            startActivity(intent);
        });

    }

}