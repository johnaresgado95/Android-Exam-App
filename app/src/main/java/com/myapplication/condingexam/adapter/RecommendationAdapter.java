package com.myapplication.condingexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapplication.condingexam.R;
import com.myapplication.condingexam.model.Products;
import com.myapplication.condingexam.screens.single_product_screen;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private Context context;
    private List<Products> productsList;

    public RecommendationAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_recommendation, parent , false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.pi_price_reco.setText(products.getOriginalPrice());
        holder.pi_description_reco.setText(products.getDescription());
        Glide.with(context).load(products.getImageUrl()).into(holder.pi_image_reco);
    }

    @Override
    public int getItemCount() {
        int limit = 7;
        if(productsList.size() > limit){
            return limit;
        }
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView pi_price_reco, pi_description_reco;
        ImageView pi_image_reco;
        CardView pi_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pi_price_reco = itemView.findViewById(R.id.pi_price_reco);
            pi_description_reco = itemView.findViewById(R.id.pi_description_reco);
            pi_image_reco = itemView.findViewById(R.id.pi_image_reco);
            pi_layout = itemView.findViewById(R.id.rec_layout);
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
