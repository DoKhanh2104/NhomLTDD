package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends BaseActivity {
ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();


        // Đặt listener cho TextView login_text
        android.widget.TextView loginText = findViewById(R.id.signup_text);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển tu LoginActivity sang SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(view -> {
            String email =binding.userEdt.getText().toString();
            String password=binding.passEdt.getText().toString();
            if(!email.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng ", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });
    }


}