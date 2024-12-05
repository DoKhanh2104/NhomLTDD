package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Domain.Order;
import com.example.foodapp.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public HistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Hiển thị thông tin mã đơn hàng và ngày đặt
        holder.orderIdTextView.setText("Mã đơn: " + order.getOrderId());
        holder.orderDateTextView.setText("Ngày: " + formatDate(order.getTimestamp()));
        holder.orderTotalTextView.setText("Tổng tiền: " + Math.floor(order.getTotalPrice()+1)  + "$");
        holder.orderStatusTextView.setText(order.getStatus());

        // Xử lý hiển thị món ăn
        LinearLayout foodsLayout = holder.foodsLayout;
        foodsLayout.removeAllViews(); // Xóa hết các món ăn cũ

        // Duyệt qua các món ăn trong đơn hàng và thêm vào layout
        for (Foods food : order.getFoods()) {
            // Tạo LinearLayout cho mỗi món ăn
            LinearLayout foodItemContainer = new LinearLayout(context);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            containerParams.setMargins(16, 0, 16, 0);
            foodItemContainer.setLayoutParams(containerParams);
            foodItemContainer.setOrientation(LinearLayout.VERTICAL);

            // Tạo ImageView cho hình ảnh món ăn
            ImageView foodImageView = new ImageView(context);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    250,
                    250);
            foodImageView.setLayoutParams(imageParams);

            // Dùng Glide để tải ảnh từ đường dẫn ImagePath
            Glide.with(holder.itemView.getContext())
                    .load(food.getImagePath())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(foodImageView);

            // Tạo TextView cho tên món ăn
            TextView foodNameTextView = new TextView(context);
            foodNameTextView.setText(food.getTitle());
            foodNameTextView.setTextSize(12);
            foodNameTextView.setGravity(Gravity.CENTER);

            // Thêm ImageView và TextView vào container
            foodItemContainer.addView(foodImageView);
            foodItemContainer.addView(foodNameTextView);

            // Thêm container vào LinearLayout chính
            foodsLayout.addView(foodItemContainer);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder cho mỗi đơn hàng
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, orderDateTextView, orderTotalTextView, orderStatusTextView;
        LinearLayout foodsLayout;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderTotalTextView);
            orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
            foodsLayout = itemView.findViewById(R.id.foodsLayout);
        }
    }

    // Hàm format ngày
    private String formatDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}

