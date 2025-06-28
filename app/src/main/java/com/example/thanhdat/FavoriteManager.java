package com.example.thanhdat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static final String PREF_NAME = "favorites_pref";
    private static final String FAVORITES_KEY = "favorites_list";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    private static FavoriteManager instance;

    // Singleton
    public static FavoriteManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteManager(context.getApplicationContext());
        }
        return instance;
    }

    private FavoriteManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Thêm vào yêu thích
    public void addFavorite(Product product) {
        List<Product> list = getFavorites();
        for (Product p : list) {
            if (p.name.equals(product.name)) {
                return; // tránh thêm trùng
            }
        }
        list.add(product);
        saveFavorites(list);
    }

    // ✅ Đổi đúng tên: removeFavorite
    public void removeFavorite(Product product) {
        List<Product> list = getFavorites();
        list.removeIf(p -> p.name.equals(product.name));
        saveFavorites(list);
    }

    // Kiểm tra sản phẩm đã được yêu thích chưa
    public boolean isFavorite(Product product) {
        for (Product p : getFavorites()) {
            if (p.name.equals(product.name)) {
                return true;
            }
        }
        return false;
    }

    // Lấy danh sách yêu thích
    public List<Product> getFavorites() {
        String json = sharedPreferences.getString(FAVORITES_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    // Lưu danh sách vào SharedPreferences
    private void saveFavorites(List<Product> list) {
        String json = gson.toJson(list);
        sharedPreferences.edit().putString(FAVORITES_KEY, json).apply();
    }
}
