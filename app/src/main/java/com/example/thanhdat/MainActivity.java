package com.example.thanhdat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btnMenu, btnSearch;
    EditText edtSearch;
    TextView tvHomeTitle, btnAllProducts, btnAllShirts, btnAllPants, btnAllShoes;

    RecyclerView recyclerView, recyclerShirts, recyclerPants, recyclerShoes;
    List<Product> productList, shirtList, pantList, shoeList;
    ProductAdapter adapter, shirtAdapter, pantAdapter, shoeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = findViewById(R.id.btnMenu);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearch);

        btnSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, AllProductActivity.class);
            intent.putExtra("keyword", keyword);
            startActivity(intent);
        });

        ImageView imgTops = findViewById(R.id.imgTops);
        ImageView imgBottoms = findViewById(R.id.imgBottoms);
        ImageView imgShoes = findViewById(R.id.imgShoes);

        imgTops.setOnClickListener(v -> startAllProductActivity("tops"));
        imgBottoms.setOnClickListener(v -> startAllProductActivity("bottoms"));
        imgShoes.setOnClickListener(v -> startAllProductActivity("shoes"));

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        tvHomeTitle = findViewById(R.id.tvHomeTitle);
        tvHomeTitle.setOnClickListener(v -> recreate());

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                recreate();
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
            } else if (id == R.id.nav_favorite) {
                startActivity(new Intent(this, FavoriteActivity.class));
            } else if (id == R.id.nav_menu) {
                startActivity(new Intent(this, UserActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        btnAllProducts = findViewById(R.id.btnAllProducts);
        btnAllProducts.setOnClickListener(v -> startAllProductActivity("all"));

        recyclerShirts = findViewById(R.id.recyclerShirts);
        recyclerShirts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        shirtList = new ArrayList<>();
        shirtAdapter = new ProductAdapter(this, shirtList);
        recyclerShirts.setAdapter(shirtAdapter);
        btnAllShirts = findViewById(R.id.btnAllShirts);
        btnAllShirts.setOnClickListener(v -> startAllProductActivity("tops"));

        recyclerPants = findViewById(R.id.recyclerPants);
        recyclerPants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        pantList = new ArrayList<>();
        pantAdapter = new ProductAdapter(this, pantList);
        recyclerPants.setAdapter(pantAdapter);
        btnAllPants = findViewById(R.id.btnAllPants);
        btnAllPants.setOnClickListener(v -> startAllProductActivity("bottoms"));

        recyclerShoes = findViewById(R.id.recyclerShoes);
        recyclerShoes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        shoeList = new ArrayList<>();
        shoeAdapter = new ProductAdapter(this, shoeList);
        recyclerShoes.setAdapter(shoeAdapter);
        btnAllShoes = findViewById(R.id.btnAllShoes);
        btnAllShoes.setOnClickListener(v -> startAllProductActivity("shoes"));

        loadProducts();
        loadShirtProducts();
        loadPantProducts();
        loadShoeProducts();

        // ✅ LƯU DANH SÁCH SẢN PHẨM VÀO SHARED PREFERENCES
        ProductManager.saveProducts(this, productList);
    }

    private void startAllProductActivity(String category) {
        Intent intent = new Intent(this, AllProductActivity.class);
        intent.putExtra("type", category);
        startActivity(intent);
    }

    private void loadProducts() {
        productList.add(new Product("Áo nữ Lamode", "$270.00", 4.9, R.drawable.img2, "tops"));
        productList.add(new Product("Quần Slim Fit", "$230.00", 4.6, R.drawable.img4, "bottoms"));
        productList.add(new Product("Giày thể thao", "$350.00", 4.8, R.drawable.img5, "shoes"));
        productList.add(new Product("Áo thun basic", "$199.00", 4.5, R.drawable.img1, "tops"));
        productList.add(new Product("Quần jeans", "$210.00", 4.2, R.drawable.img3, "bottoms"));
        productList.add(new Product("Giày da lười", "$299.00", 4.3, R.drawable.img6, "shoes"));
        productList.add(new Product("Quan nữ", "$29.00", 4.3, R.drawable.img7, "bottoms"));
        productList.add(new Product("Giày nữ", "$99.00", 4.3, R.drawable.img8, "shoes"));
        productList.add(new Product("Áo nữ", "$70.00", 4.8, R.drawable.img9, "tops"));
        productList.add(new Product("Áo thun nam", "$10.00", 4.7, R.drawable.img10, "tops"));
        productList.add(new Product("Áo tay dài nam", "$30.00", 4.5, R.drawable.img11, "tops"));
        adapter.notifyDataSetChanged();
    }

    private void loadShirtProducts() {
        shirtList.clear();
        for (Product p : productList) {
            if (p.getCategory().equals("tops")) shirtList.add(p);
        }
        shirtAdapter.notifyDataSetChanged();
    }

    private void loadPantProducts() {
        pantList.clear();
        for (Product p : productList) {
            if (p.getCategory().equals("bottoms")) pantList.add(p);
        }
        pantAdapter.notifyDataSetChanged();
    }

    private void loadShoeProducts() {
        shoeList.clear();
        for (Product p : productList) {
            if (p.getCategory().equals("shoes")) shoeList.add(p);
        }
        shoeAdapter.notifyDataSetChanged();
    }
}
