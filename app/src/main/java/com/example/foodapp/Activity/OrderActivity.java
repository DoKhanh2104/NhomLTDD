package com.example.foodapp.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.OrderAdapter;
import com.example.foodapp.Domain.Order;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderTrackingAdapter;
    private List<Order> orderList;
    private ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        orderList = new ArrayList<>();

        //Truyền Context vào khi khởi tạo adapter
        orderTrackingAdapter = new OrderAdapter(orderList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderTrackingAdapter);

        loadOrders();  // Load dữ liệu đơn hàng từ Firebase

        //bị lỗi tự về trang chủ
        //setVariable();
    }

    private void loadOrders() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {  // Kiểm tra xem dữ liệu có tồn tại không
                    orderList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Order order = snapshot.getValue(Order.class);
                        if (order != null) {
                            orderList.add(order);
                        }
                    }
                    orderTrackingAdapter.notifyDataSetChanged();
                } else {
                    // Xử lý nếu không có đơn hàng nào
                    Toast.makeText(OrderActivity.this, "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderActivity.this, "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Thiết lập các sự kiện (ví dụ: quay lại)
    //private void setVariable() {
    //    binding.backBtn.setOnClickListener(view -> finish());
    //}
}
