package com.example.foodapp.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodActivity extends BaseActivity {
    private EditText edtFoodTitle, edtFoodDescription, edtFoodPrice, edtFoodImagePath,edtFoodStar,edtFoodCategoryId;
    private Button btnAddFood;
    private Button btnBack;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Anh xa
        edtFoodTitle = findViewById(R.id.edtFoodTitle);
        edtFoodDescription = findViewById(R.id.edtFoodDescription);
        edtFoodPrice = findViewById(R.id.edtFoodPrice);
        edtFoodImagePath = findViewById(R.id.edtFoodImagePath);
        btnAddFood = findViewById(R.id.btnAddFood);
        edtFoodCategoryId =findViewById(R.id.edtFoodCategoryId);
        btnBack = findViewById(R.id.btnBack);

        // Tham chiếu Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        // Xử lý khi bấm nút "Thêm món ăn"
        btnAddFood.setOnClickListener(view -> addFoodToFirebase());

        //Quay lai
        btnBack.setOnClickListener(view -> finish());
    }

    private void addFoodToFirebase() {
        // Lấy dữ liệu từ form
        String title = edtFoodTitle.getText().toString().trim();
        String description = edtFoodDescription.getText().toString().trim();
        String priceStr = edtFoodPrice.getText().toString().trim();
        String imagePath = edtFoodImagePath.getText().toString().trim();
//        String starStr = edtFoodStar.getText().toString().trim();
        String categoryIdStr = edtFoodCategoryId.getText().toString().trim();

        // Kiểm tra nhập liệu
        if (title.isEmpty() || description.isEmpty() || priceStr.isEmpty() || imagePath.isEmpty()
                 || categoryIdStr.isEmpty()) {
            Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi dữ liệu
        double price;
        double star;
        int CategoryId;

        try {
            price = Double.parseDouble(priceStr);
            //star = Double.parseDouble(starStr);
            CategoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá, số sao hoặc mã danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy tham chiếu tới Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        // Truy vấn để tìm Id lớn nhất
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                int maxId = 0;
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Foods food = snapshot.getValue(Foods.class);
                    if (food != null && food.getId() > maxId) {
                        maxId = food.getId();
                    }
                }

                // Gán Id mới
                int newId = maxId + 1;

                // Tạo món ăn mới
                String foodId = databaseReference.push().getKey(); // Firebase key
                Foods newFood = new Foods();
                newFood.setId(newId); // Gán Id tự tăng
                newFood.setTitle(title);
                newFood.setDescription(description);
                newFood.setPrice(price);
                newFood.setImagePath(imagePath);
                newFood.setStar(4.5);
                newFood.setTimeValue(15);
                newFood.setCategoryId(CategoryId);

                // Lưu món ăn vào Firebase
                databaseReference.child(foodId).setValue(newFood)
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Thêm thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "Không thể lấy dữ liệu món ăn hiện tại", Toast.LENGTH_SHORT).show();
            }
        });
    }


}