package com.example.thanhdat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final String PREF_NAME = "cart_pref";
    private static final String CART_KEY = "cart_items";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    // Singleton
    private static CartManager instance;

    public static CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    private CartManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Thêm 1 sản phẩm vào giỏ hàng
    public void addToCart(Product product) {
        List<Product> cartItems = getCartItems();
        cartItems.add(product);
        saveCartItems(cartItems);
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<Product> getCartItems() {
        String json = sharedPreferences.getString(CART_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    // Xoá toàn bộ giỏ hàng
    public void clearCart() {
        sharedPreferences.edit().remove(CART_KEY).apply();
    }

    // Cập nhật giỏ hàng (ghi đè danh sách)
    public void updateCart(List<Product> cartItems) {
        saveCartItems(cartItems);
    }

    // Lưu giỏ hàng (tên khác nhưng chức năng giống updateCart)
    public void saveCart(List<Product> cartItems) {
        saveCartItems(cartItems);
    }

    // Hàm nội bộ dùng chung để lưu dữ liệu
    private void saveCartItems(List<Product> cartItems) {
        String json = gson.toJson(cartItems);
        sharedPreferences.edit().putString(CART_KEY, json).apply();
    }
}
