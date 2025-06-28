package com.example.thanhdat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class    ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productRating;
    private Button btnAddToCart, btnFavorite;

    private String name, price;
    private double rating;
    private int imageRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Nút quay lại
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Ánh xạ view
        productImage = findViewById(R.id.detailImage);
        productName = findViewById(R.id.detailName);
        productPrice = findViewById(R.id.detailPrice);
        productRating = findViewById(R.id.detailRating);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnFavorite = findViewById(R.id.btnFavorite); // nút yêu thích

        // Nhận dữ liệu từ Intent
        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            price = getIntent().getStringExtra("price");
            rating = getIntent().getDoubleExtra("rating", 0.0);
            imageRes = getIntent().getIntExtra("image", R.drawable.img1); // hình mặc định

            // Hiển thị lên giao diện
            productName.setText(name);
            productPrice.setText(price);
            productRating.setText("Đánh giá: " + rating);
            productImage.setImageResource(imageRes);
        }

        // Tạo sản phẩm hiện tại
        Product product = new Product(name, price, rating, imageRes, "");

        // Cập nhật trạng thái nút yêu thích ban đầu
        if (FavoriteManager.getInstance(this).isFavorite(product)) {
            btnFavorite.setText("♥ Đã yêu thích");
        } else {
            btnFavorite.setText("♥ Yêu thích");
        }

        // Xử lý nút Thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance(this).addToCart(product);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // Xử lý nút Yêu thích
        btnFavorite.setOnClickListener(v -> {
            FavoriteManager manager = FavoriteManager.getInstance(this);
            if (manager.isFavorite(product)) {
                manager.removeFavorite(product);
                btnFavorite.setText("♥ Yêu thích");
                Toast.makeText(this, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                manager.addFavorite(product);
                btnFavorite.setText("♥ Đã yêu thích");
                Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
