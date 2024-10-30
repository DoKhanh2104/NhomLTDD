package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.foodapp.Fragment.ThongBaoFragment;
import com.example.foodapp.Fragment.TroChuyenFragment;
import com.example.foodapp.R;

public class SupportActivity extends AppCompatActivity {
    Button btnTC,btnTB;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        fragmentManager = getSupportFragmentManager();

        frameLayout = findViewById(R.id.frame_content);
        btnTC = findViewById(R.id.btnTC);
        btnTB = findViewById(R.id.btnTB);

        //Tạo sự kiện chuyển sang fragment trò chuyện
        btnTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TroChuyenFragment troChuyenFragment = new TroChuyenFragment();
                fragmentManager.beginTransaction().replace(R.id.frame_content,troChuyenFragment).commit();
            }
        });

        //Tạo sự kiện chuyển sang fragment thông báo
        btnTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongBaoFragment thongBaoFragment=new ThongBaoFragment();
                fragmentManager.beginTransaction().replace(R.id.frame_content,thongBaoFragment).commit();
            }
        });


    }
}