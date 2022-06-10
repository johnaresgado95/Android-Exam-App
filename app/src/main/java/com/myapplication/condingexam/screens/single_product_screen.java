package com.myapplication.condingexam.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.myapplication.condingexam.R;

import java.util.ArrayList;
import java.util.List;

public class single_product_screen extends AppCompatActivity {

    CollapsingToolbarLayout collapseActionView;
    ImageView iv_single;
    TextView single_name, single_price, single_description, single_sales_price, single_sale;
    ToggleButton size_s;

    @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_screen);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        collapseActionView = findViewById(R.id.collapseActionView);
        iv_single = findViewById(R.id.iv_single);
        single_name = findViewById(R.id.single_name);
        single_price = findViewById(R.id.single_price);
        single_description = findViewById(R.id.single_description);
        single_sales_price = findViewById(R.id.single_sales_price);
        single_sale = findViewById(R.id.single_sale);

        collapseActionView.setTitle(bundle.getString("name"));
        single_name.setText(bundle.getString("name"));
        single_price.setText("US " + bundle.getString("originalPrice"));
        single_description.setText(bundle.getString("description"));
        single_sale.setText(bundle.getString("percentOff") + "% Off");
        single_sales_price.setText("US " + bundle.getString("salePrice"));

        Glide.with(single_product_screen.this).load(bundle.getString("imageUrl")).into(iv_single);
    }
}