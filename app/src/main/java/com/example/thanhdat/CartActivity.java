package com.example.thanhdat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangedListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Product> cartList;

    private TextView txtTotal;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtTotal = findViewById(R.id.txtTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Lấy danh sách từ CartManager
        cartList = CartManager.getInstance(this).getCartItems();

        // Dùng CartAdapter thay vì ProductAdapter
        adapter = new CartAdapter(this, cartList, this);
        recyclerView.setAdapter(adapter);

        calculateTotal();

        ImageView btnBack = findViewById(R.id.btnBackCart);
        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            // Xoá giỏ
            CartManager.getInstance(this).clearCart();
            cartList.clear();
            adapter.notifyDataSetChanged();
            calculateTotal();
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (Product p : cartList) {
            try {
                total += Double.parseDouble(p.price.replace("$", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txtTotal.setText("Tổng: $" + total);
    }

    @Override
    public void onCartUpdated() {
        // Gọi lại để cập nhật tổng tiền sau khi xóa sản phẩm
        calculateTotal();
    }
}
