package com.example.foodapp.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodapp.Domain.Foods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagmentFavorite {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private static final String FAVORITE_PREFS = "FavoritePrefs";
    private static final String FAVORITE_LIST_KEY = "favoriteList";

    public ManagmentFavorite(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FAVORITE_PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Lấy danh sách yêu thích
    public ArrayList<Foods> getFavoriteList() {
        String json = sharedPreferences.getString(FAVORITE_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Foods>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }

    // Thêm món ăn vào danh sách yêu thích
    public void addFavorite(Foods food) {
        ArrayList<Foods> favoriteList = getFavoriteList();

        // Kiểm tra trùng lặp
        if (!isFavorite(food)) {
            favoriteList.add(food);
            saveFavoriteList(favoriteList);
        }
    }

    // Xóa món ăn khỏi danh sách yêu thích
    public void removeFavorite(Foods food) {
        ArrayList<Foods> favoriteList = getFavoriteList();

        // Tìm và xóa món ăn khỏi danh sách
        favoriteList.removeIf(f -> f.getTitle().equals(food.getTitle()));
        saveFavoriteList(favoriteList);
    }

    // Kiểm tra xem món ăn có trong danh sách yêu thích hay không
    public boolean isFavorite(Foods food) {
        ArrayList<Foods> favoriteList = getFavoriteList();
        for (Foods f : favoriteList) {
            if (f.getTitle().equals(food.getTitle())) {
                return true;
            }
        }
        return false;
    }

    // Lưu danh sách yêu thích
    public void saveFavoriteList(ArrayList<Foods> favoriteList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FAVORITE_LIST_KEY, gson.toJson(favoriteList));
        editor.apply();
    }
}
