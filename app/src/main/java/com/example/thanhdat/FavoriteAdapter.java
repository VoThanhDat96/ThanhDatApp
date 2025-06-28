package com.example.thanhdat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final Context context;
    private List<Product> favorites;
    private final OnFavoriteChangedListener listener;

    public interface OnFavoriteChangedListener {
        void onFavoriteUpdated();
    }

    public FavoriteAdapter(Context context, List<Product> favorites, OnFavoriteChangedListener listener) {
        this.context = context;
        this.favorites = favorites;
        this.listener = listener;
    }

    public void setData(List<Product> newList) {
        this.favorites = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productRating;
        Button btnRemoveFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productRating = itemView.findViewById(R.id.productRating);
            btnRemoveFavorite = itemView.findViewById(R.id.btnRemove);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = favorites.get(position);
        holder.productImage.setImageResource(p.imageRes);
        holder.productName.setText(p.name);
        holder.productPrice.setText(p.price);
        holder.productRating.setText(String.valueOf(p.rating));

        holder.btnRemoveFavorite.setOnClickListener(v -> {
            FavoriteManager.getInstance(context).removeFavorite(p);
            favorites.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            listener.onFavoriteUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }
}
