package com.example.thanhdat;

public class Product {
    public String name;
    public String price;
    public double rating;
    public int imageRes;
    public String category;

    // Constructor mặc định cho Gson
    public Product() {
    }

    public Product(String name, String price, double rating, int imageRes, String category) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.imageRes = imageRes;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    // Thêm để so sánh sản phẩm trong danh sách (cart/favorite)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;

        // So sánh theo name (có thể thêm category nếu cần)
        return name != null && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
