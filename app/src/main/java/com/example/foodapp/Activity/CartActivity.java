package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.CartAdapter;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Domain.Order;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.databinding.ActivityCartBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    private double total;
    private final double delivery = 10;
    private final double discount = 0.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        caculateCart();
        initList();
    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> caculateCart());
        binding.cardView.setAdapter(adapter);
    }

    private void caculateCart() {
        double percentTax = 0.02;

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        // Tính tổng đơn hàng, có tính thuế, phí giao hàng và giảm giá (nếu có)
        total = Math.round((managmentCart.getTotalFee() + tax + delivery) * (1 - discount) * 100) / 100;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());

        // Đặt hàng
        binding.button2.setOnClickListener(view -> {
            // Lấy tham chiếu đến Firebase Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");

            // Tạo danh sách tên món ăn từ giỏ hàng
            List<String> foodTitles = new ArrayList<>();
            for (Foods food : managmentCart.getListCart()) {
                foodTitles.add(food.getTitle());

            }

            // Tạo đối tượng Order với các thông tin từ giỏ hàng
            Order order = new Order(foodTitles, total, tax, delivery);

            // Tạo ID đơn hàng duy nhất
            String orderId = databaseReference.push().getKey();

            // Lưu đơn hàng vào Firebase Realtime Database
            if (orderId != null) {
                databaseReference.child(orderId).setValue(order)
                        .addOnSuccessListener(aVoid -> {
                            // Xử lý khi lưu thành công
                            Toast.makeText(CartActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                            // Xóa giỏ hàng sau khi lưu đơn hàng
                            managmentCart.clear();
                        })
                        .addOnFailureListener(e -> {
                            // Xử lý khi lưu thất bại
                            Toast.makeText(CartActivity.this, "Lỗi khi lưu đơn hàng!", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Kiểm tra mã giảm giá
        binding.button.setOnClickListener(view -> {
            String enteredCode = binding.editTextText.getText().toString();
            if (isValidDiscountCode(enteredCode)) {
                double discountAmount = total * discount;
                total = total - discountAmount;
                binding.totalTxt.setText("$" + total);
                Toast.makeText(CartActivity.this, "Mã giảm giá hợp lệ!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartActivity.this, "Mã giảm giá không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm kiểm tra mã giảm giá
    private boolean isValidDiscountCode(String code) {
        return "0000".equals(code);
    }
}
