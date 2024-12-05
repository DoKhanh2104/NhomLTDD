package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.Helper.ManagmentFavorite;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num=1;
    private ManagmentCart managmentCart;
    private ManagmentFavorite managmentFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart=new ManagmentCart(this);
        managmentFavorite = new ManagmentFavorite(this);
        //Quay lai
        binding.backBtn.setOnClickListener(view -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$"+object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num * object.getPrice() + "$"));

        //Tang so luong
        binding.plusBtn.setOnClickListener(view -> {
            num=num+1;
            binding.numTxt.setText(num+" ");
            binding.totalTxt.setText("$"+(num*object.getPrice()));
        });
        //Giam so luong
        binding.minusBtn.setOnClickListener(view -> {
            if(num>1){
                num=num-1;
                binding.numTxt.setText(num+"");
                binding.totalTxt.setText("$"+(num*object.getPrice()));
            }
        });
        //Them vao gio hang
        binding.addBtn.setOnClickListener(view -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });

        // Thêm vào danh sách yêu thích
        binding.favBtn.setOnClickListener(view -> {
            if (!managmentFavorite.isFavorite(object)) {
                managmentFavorite.addFavorite(object);
                Toast.makeText(DetailActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, "Món ăn đã có trong danh sách yêu thích!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentExtra() {
        object= (Foods) getIntent().getSerializableExtra("object");
    }
}