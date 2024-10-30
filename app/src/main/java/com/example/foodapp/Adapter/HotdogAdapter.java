package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Domain.Hotdog;
import com.example.foodapp.R;

import java.util.List;

public class HotdogAdapter extends RecyclerView.Adapter<HotdogAdapter.HotdogViewHolder> {

    private Context context;
    private List<Hotdog> hotdogList;

    public HotdogAdapter(Context context, List<Hotdog> hotdogList) {
        this.context = context;
        this.hotdogList = hotdogList;
    }

    @NonNull
    @Override
    public HotdogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotdog, parent, false);
        return new HotdogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotdogViewHolder holder, int position) {
        Hotdog hotdog = hotdogList.get(position);
        holder.nameTextView.setText(hotdog.getName());
        holder.timeTextView.setText(hotdog.getTime() + " min");
        holder.priceTextView.setText("$" + hotdog.getPrice());
        holder.ratingBar.setRating((float) hotdog.getRating());
        // Set image nếu có sử dụng hình ảnh từ drawable hoặc tải từ URL
        holder.imageView.setImageResource(hotdog.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return hotdogList.size();
    }

    public static class HotdogViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, timeTextView, priceTextView;
        RatingBar ratingBar;

        public HotdogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotdog_image);
            nameTextView = itemView.findViewById(R.id.hotdog_name);
            timeTextView = itemView.findViewById(R.id.hotdog_time);
            priceTextView = itemView.findViewById(R.id.hotdog_price);
            ratingBar = itemView.findViewById(R.id.hotdog_rating);
        }
    }
}
