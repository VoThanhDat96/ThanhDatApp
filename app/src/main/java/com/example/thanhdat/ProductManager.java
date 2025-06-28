package com.example.thanhdat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static final String PREF_NAME = "product_pref";
    private static final String KEY_PRODUCTS = "products";

    public static void saveProducts(Context context, List<Product> productList) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(productList);
        prefs.edit().putString(KEY_PRODUCTS, json).apply();
    }

    public static List<Product> getProducts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PRODUCTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }
}
