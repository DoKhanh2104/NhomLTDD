package com.example.foodapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Activity.DetailAdminActivity;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Foods> foodList;
    private DatabaseReference databaseReference;

    public FoodAdapter(Context context, List<Foods> foodList) {
        this.context = context;
        this.foodList = foodList;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Foods food = foodList.get(position);
        holder.foodTitle.setText(food.getTitle());
        holder.foodPrice.setText("$" + food.getPrice());


        // Mở màn hình chi tiết món ăn khi nhấn vào item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailAdminActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });

        Glide.with(context)
                .load(food.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.foodImage);

        holder.deleteBtn.setOnClickListener(view -> {
            databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

            databaseReference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Foods tempFood = dataSnapshot.getValue(Foods.class);
                        if (tempFood != null && tempFood.getId() == food.getId()) {
                            String foodKey = dataSnapshot.getKey();
                            if (foodKey != null) {
                                databaseReference.child(foodKey).removeValue()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            // Tìm và xóa item trong danh sách cục bộ
                                            int indexToRemove = -1;
                                            for (int i = 0; i < foodList.size(); i++) {
                                                if (foodList.get(i).getId() == food.getId()) {
                                                    indexToRemove = i;
                                                    break;
                                                }
                                            }
                                            if (indexToRemove != -1) {
                                                foodList.remove(indexToRemove);
                                                notifyItemRemoved(indexToRemove);
                                            } else {
                                                Toast.makeText(context, "Không tìm thấy món ăn trong danh sách!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show());
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                    Toast.makeText(context, "Lỗi Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodTitle, foodPrice;
        ImageView foodImage, deleteBtn;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.foodTitle);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodImage = itemView.findViewById(R.id.foodImage);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
