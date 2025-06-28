package com.example.thanhdat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> allProducts;
    private List<Product> filteredProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        recyclerView = findViewById(R.id.recyclerViewAll);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Lấy danh sách sản phẩm đã lưu từ MainActivity
        allProducts = ProductManager.getProducts(this);
        filteredProducts = new ArrayList<>();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String keyword = intent.getStringExtra("keyword");

        if (keyword != null && !keyword.isEmpty()) {
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredProducts.add(p);
                }
            }
        } else if (type != null && !type.equals("all")) {
            for (Product p : allProducts) {
                if (p.getCategory().equalsIgnoreCase(type)) {
                    filteredProducts.add(p);
                }
            }
        } else {
            filteredProducts.addAll(allProducts);
        }

        productAdapter = new ProductAdapter(this, filteredProducts);
        recyclerView.setAdapter(productAdapter);


        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
