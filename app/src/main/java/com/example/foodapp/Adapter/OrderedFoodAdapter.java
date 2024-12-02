package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;

import java.util.ArrayList;

public class OrderedFoodAdapter extends RecyclerView.Adapter<OrderedFoodAdapter.ViewHolder> {

    private ArrayList<Foods> orderedFoods;
    private Context context;

    public OrderedFoodAdapter(ArrayList<Foods> orderedFoods, Context context) {
        this.orderedFoods = orderedFoods;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food_ordered, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods food = orderedFoods.get(position);
        holder.foodTitle.setText(food.getTitle());
        holder.foodQuantity.setText("Số lượng: " + food.getNumberInCart());
        holder.foodPrice.setText("$" + (food.getNumberInCart() * food.getPrice()));

        Glide.with(context)
                .load(food.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return orderedFoods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodTitle, foodQuantity, foodPrice;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.foodTitle);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodImage = itemView.findViewById(R.id.foodImage);
        }
    }
}
