package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();

        binding.signText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    private void setVariable() {
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= binding.userEdt.getText().toString();
                String password=binding.passEdt.getText().toString();

                if (password.length()<6){
                    Toast.makeText(SignUpActivity.this,"Mật khẩu của bạn phải trên 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "onComplete: ");
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        }else  {
                            Log.i(TAG, "failure: "+task.getException() );
                            Toast.makeText(SignUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
