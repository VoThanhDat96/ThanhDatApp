package com.example.thanhdat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    TextView txtName, txtEmail, txtPhone;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtUserEmail);
        txtPhone = findViewById(R.id.txtUserPhone); // ✅ ánh xạ thêm số điện thoại
        btnLogout = findViewById(R.id.btnLogout);
        ImageView btnBack = findViewById(R.id.btnBackUser);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "Guest");
        String email = prefs.getString("email", "No Email");
        String phone = prefs.getString("phone", "Chưa cập nhật");

        txtName.setText("Tên: " + name);
        txtEmail.setText("Email: " + email);
        txtPhone.setText("SĐT: " + phone);

        btnBack.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
