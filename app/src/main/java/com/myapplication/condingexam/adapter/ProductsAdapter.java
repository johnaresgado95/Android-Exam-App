package com.myapplication.condingexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapplication.condingexam.R;
import com.myapplication.condingexam.model.Products;
import com.myapplication.condingexam.screens.single_product_screen;

import java.util.List;
import java.util.Random;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Products> productsList;

    public static int itemcount = 0;

    public ProductsAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent , false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.pi_price.setText(products.getOriginalPrice());
        holder.pi_sale.setText(products.getPercentOff() +"% Off");
        Glide.with(context).load(products.getImageUrl()).into(holder.pi_image);


    }

    @Override
    public int getItemCount() {
        if(productsList.size() > itemcount){
            return itemcount;
        }
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView pi_price, pi_sale;
        ImageView pi_image;
        CardView pi_layout;
        RatingBar simpleRatingBar;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            pi_price = itemView.findViewById(R.id.pi_price);
            pi_sale = itemView.findViewById(R.id.pi_sale);
            pi_image = itemView.findViewById(R.id.pi_image);
            pi_layout = itemView.findViewById(R.id.pi_layout);
            simpleRatingBar = itemView.findViewById(R.id.simpleRatingBar);

            Random rand = new Random();
            int max = 5;
            int min = 1;
            int randomInt = rand.nextInt((max - min) + max) + min;
            float randomNum = randomInt / 2.4f;

            simpleRatingBar.setRating(randomNum);

            pi_layout.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            Products productModel = productsList.get(getAdapterPosition());

            Intent i = new Intent(context, single_product_screen.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",productModel.getName());
            bundle.putString("imageUrl",productModel.getImageUrl());
            bundle.putString("originalPrice",productModel.getOriginalPrice());
            bundle.putString("description",productModel.getDescription());
            bundle.putString("salePrice",productModel.getSalePrice());
            bundle.putString("percentOff",productModel.getPercentOff());
            i.putExtras(bundle);
            context.startActivity(i);
        }
    }
}
