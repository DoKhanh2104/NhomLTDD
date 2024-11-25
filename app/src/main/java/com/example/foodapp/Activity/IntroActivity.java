package com.example.foodapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();


    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(view -> {
            if(mAuth.getCurrentUser()!=null){
                startActivity(new Intent(IntroActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(IntroActivity.this,LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(view -> startActivity(new Intent(IntroActivity.this,SignUpActivity.class)));
    }

}
