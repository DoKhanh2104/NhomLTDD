package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Domain.Order;
import com.example.foodapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        if (order != null) {
            // Hiển thị tổng giá trị, thuế và phí giao hàng
            holder.totalTextView.setText("Total: $" + order.getTotal());
            holder.taxTextView.setText("Tax: $" + order.getTax());
            holder.deliveryTextView.setText("Delivery: $" + order.getDelivery());

            // Hiển thị danh sách tên món ăn
            List<String> foodTitles = order.getFoodsList();
            if (foodTitles != null && !foodTitles.isEmpty()) {
                StringBuilder foodsText = new StringBuilder("Foods: ");
                for (String foodTitle : foodTitles) {
                    foodsText.append(foodTitle).append(", ");
                }
                holder.foodsListTextView.setText(foodsText.substring(0, foodsText.length() - 2));
            } else {
                holder.foodsListTextView.setText("Foods: None");
            }

            // Set hình ảnh món ăn
            if (foodTitles != null && !foodTitles.isEmpty()) {
                holder.foodImageView.setImageResource(R.drawable.food);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView totalTextView, taxTextView, deliveryTextView, foodsListTextView;
        ImageView foodImageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            taxTextView = itemView.findViewById(R.id.taxTextView);
            deliveryTextView = itemView.findViewById(R.id.deliveryTextView);
            foodsListTextView = itemView.findViewById(R.id.foodsListTextView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
        }
    }
}
