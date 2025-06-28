package com.example.thanhdat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private List<Product> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteList = FavoriteManager.getInstance(this).getFavorites();

        if (favoriteList.isEmpty()) {
            Toast.makeText(this, "Chưa có sản phẩm yêu thích nào", Toast.LENGTH_SHORT).show();
        }

        adapter = new FavoriteAdapter(this, favoriteList, () -> {
            // Khi xoá 1 sản phẩm, cập nhật lại danh sách
            favoriteList = FavoriteManager.getInstance(this).getFavorites();
            adapter.setData(favoriteList);
        });

        recyclerView.setAdapter(adapter);

        ImageView btnBack = findViewById(R.id.btnBackFavorite);
        btnBack.setOnClickListener(v -> finish());
    }
}
