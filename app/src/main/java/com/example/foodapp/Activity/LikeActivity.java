package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.Adapter.FavoriteAdapter;
import com.example.foodapp.Helper.ManagmentFavorite;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.databinding.ActivityLikeBinding;

import java.util.ArrayList;

public class LikeActivity extends BaseActivity {
    private ActivityLikeBinding binding;
    private ManagmentFavorite managmentFavorite;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo đối tượng quản lý danh sách yêu thích
        managmentFavorite = new ManagmentFavorite(this);

        // Thiết lập các biến và RecyclerView
        setVariable();
        initList();
    }

    // Khởi tạo danh sách yêu thích và cập nhật giao diện
    private void initList() {
        // Lấy danh sách yêu thích từ ManagmentFavorite
        ArrayList<Foods> favoriteList = managmentFavorite.getFavoriteList();

        // Kiểm tra nếu danh sách rỗng và hiển thị thông báo phù hợp
        if (favoriteList.isEmpty()) {
            showEmptyListMessage();
        } else {
            showFavoriteList(favoriteList);
        }
    }

    // Hiển thị thông báo danh sách trống
    private void showEmptyListMessage() {
        binding.emptyTxt.setVisibility(View.VISIBLE); // Hiển thị thông báo danh sách trống
        binding.cardView.setVisibility(View.GONE); // Ẩn RecyclerView nếu danh sách trống
    }

    // Hiển thị danh sách yêu thích trong RecyclerView
    private void showFavoriteList(ArrayList<Foods> favoriteList) {
        binding.emptyTxt.setVisibility(View.GONE); // Ẩn thông báo danh sách trống
        binding.cardView.setVisibility(View.VISIBLE); // Hiển thị RecyclerView nếu có dữ liệu

        // Thiết lập RecyclerView với Adapter
        adapter = new FavoriteAdapter(favoriteList, this);
        binding.cardView.setLayoutManager(new LinearLayoutManager(this));
        binding.cardView.setAdapter(adapter);
    }

    // Cập nhật giao diện khi danh sách trống
    public void updateUIForEmptyList() {
        showEmptyListMessage();
    }

    // Quay lai
    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}
