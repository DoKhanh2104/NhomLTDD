package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityDetailAdminBinding;

public class DetailAdminActivity extends BaseActivity {
    ActivityDetailAdminBinding binding;
    private Foods object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Cac phuong thuc
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        //Quay lai
        binding.backBtn.setOnClickListener(view -> finish());
        //Truyen du lieu vao trang Detail
        Glide.with(DetailAdminActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$"+object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        //binding.totalTxt.setText((object.getPrice() + "$"));
    }

    private void getIntentExtra() {
        object=(Foods) getIntent().getSerializableExtra("object");
    }
}