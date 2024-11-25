package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Activity.LikeActivity;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Helper.ManagmentFavorite;
import com.example.foodapp.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<Foods> list;
    private Context context;
    private ManagmentFavorite managmentFavorite;

    public FavoriteAdapter(ArrayList<Foods> list, Context context) {
        this.list = list;
        this.context = context;
        this.managmentFavorite = new ManagmentFavorite(context); // Khởi tạo ManagmentFavorite
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_like, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Foods food = list.get(position);

        holder.title.setText(food.getTitle());
        holder.totalEachItem.setText("$" + food.getPrice());
        holder.time.setText(food.getTimeValue() + " min");
        Glide.with(context)
                .load(food.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        // Mở màn hình chi tiết món ăn khi nhấn vào item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });

        // Xử lý sự kiện xóa món ăn khỏi danh sách yêu thích
        holder.deleteIcon.setOnClickListener(view -> {
            // Kiểm tra nếu danh sách không rỗng và chỉ mục hợp lệ
            if (list != null && position >= 0 && position < list.size()) {
                // Xóa món ăn khỏi danh sách yêu thích
                managmentFavorite.removeFavorite(food);  // Xóa khỏi SharedPreferences

                // Xóa item khỏi danh sách RecyclerView
                list.remove(position);
                notifyItemRemoved(position);  // Cập nhật RecyclerView

                // Nếu danh sách còn phần tử thì làm mới lại RecyclerView
                if (list.size() > 0) {
                    notifyItemRangeChanged(position, list.size());  // Làm mới các item còn lại sau khi xóa
                }

                // Kiểm tra xem danh sách có trống hay không
                if (list.isEmpty()) {
                    ((LikeActivity) context).updateUIForEmptyList();
                }

                // Lưu lại danh sách đã cập nhật vào SharedPreferences
                managmentFavorite.saveFavoriteList(list);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, totalEachItem, time;
        ImageView pic, deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            time = itemView.findViewById(R.id.timeTxt);
            deleteIcon = itemView.findViewById(R.id.deleteIcon); // Icon xóa
        }
    }
}
