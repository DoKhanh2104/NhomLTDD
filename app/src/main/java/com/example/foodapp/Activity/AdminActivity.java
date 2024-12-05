package com.example.foodapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends BaseActivity {
    private RecyclerView foodRecyclerView;
    private FoodAdapter foodAdapter;
    private List<Foods> foodList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private Button addBtn;
    private ImageView logoutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Dang xuat
        logoutBtn =findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Chuyen sang trang them moi mon an
        addBtn =findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });

        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        // Lấy dữ liệu từ Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Foods food = dataSnapshot.getValue(Foods.class);
                    foodList.add(food);
                }
                foodAdapter = new FoodAdapter(AdminActivity.this, foodList);
                foodRecyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

}