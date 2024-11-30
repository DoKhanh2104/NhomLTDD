package com.example.foodapp.Activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.CartAdapter;
import com.example.foodapp.Adapter.OrderedFoodAdapter;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityOrderProgressBinding;

import java.util.ArrayList;

public class OrderProgressActivity extends BaseActivity {
    private int currentStep = 1;
    private ActivityOrderProgressBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView orderedFoodList;
    private OrderedFoodAdapter orderedFoodAdapter;
    private ManagmentCart managmentCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);


        orderedFoodList = binding.orderedFoodList;
        orderedFoodList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<Foods> orderedFoods = managmentCart.getListCart(); // Lấy danh sách từ giỏ hàng
        orderedFoodAdapter = new OrderedFoodAdapter(orderedFoods, this);
        orderedFoodList.setAdapter(orderedFoodAdapter);


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Cập nhật giao diện theo từng bước
        updateStep();

        // Set click listener cho từng bước
        binding.statusText.setOnClickListener(v -> {
            if (currentStep < 3) {
                currentStep++;
                updateStep();
            }
        });

        // Nút hoàn thành
        binding.completeButton.setOnClickListener(v -> {
            managmentCart.clearCart();
            Toast.makeText(this, "Đơn hàng đã hoàn tất!", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(OrderProgressActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }



    private void updateStep() {


        switch (currentStep) {
            case 1:
                binding.step1Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));
                binding.statusText.setText("Đang xử lý...");
                animateProgress(binding.progressBar1, 0);
                break;
            case 2:
                binding.step1Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));

                binding.statusText.setText("Đang giao hàng...");
                animateProgress(binding.progressBar1, 100); // Chạy thanh nối bước 1-2
                animateProgress(binding.progressBar2, 0);   // Đặt thanh nối bước 2-3 về ban đầu
                binding.step2Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));
                break;
            case 3:
                binding.step1Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));
                binding.step2Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));
                binding.step3Icon.setColorFilter(ContextCompat.getColor(this, R.color.red));
                binding.statusText.setText("Hoàn tất!");
                binding.completeButton.setVisibility(View.VISIBLE);
                animateProgress(binding.progressBar2, 100); // Chạy thanh nối bước 2-3
                break;
        }

    }
    private void animateProgress(ProgressBar progressBar, int targetProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", targetProgress);
        animator.setDuration(1000); // Thời gian chạy (1000ms = 1 giây)
        animator.start();
    }


}