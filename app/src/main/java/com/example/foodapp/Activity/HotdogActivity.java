package com.example.foodapp.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.HotdogAdapter;
import com.example.foodapp.Domain.Hotdog;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class HotdogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotdog);

        List<Hotdog> hotdogList = new ArrayList<>();
        hotdogList.add(new Hotdog("Thịt Xông Khói Phô Mai", 9, 5.99, 4.2,R.drawable.baconandcheeseheaven));
        hotdogList.add(new Hotdog("Thịt Bò Xông Khói", 9, 5.99, 4.2,R.drawable.baconfiletmignon));
        hotdogList.add(new Hotdog("Hamburger", 9, 5.99, 4.2,R.drawable.bbqranchdelight));
        hotdogList.add(new Hotdog("Sinh Tố Berry", 9, 5.99, 4.2,R.drawable.berryblastsmoothie));
        hotdogList.add(new Hotdog("Pizza Gà", 9, 5.99, 4.2,R.drawable.bbqchickendelight));
        hotdogList.add(new Hotdog("Thịt Bò Xào Bông Cải", 9, 5.99, 4.2,R.drawable.beefstirfrywithbroccoli));
        // Thêm các mục hotdog khác

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        HotdogAdapter adapter = new HotdogAdapter(this, hotdogList);
        recyclerView.setAdapter(adapter);
    }
}
