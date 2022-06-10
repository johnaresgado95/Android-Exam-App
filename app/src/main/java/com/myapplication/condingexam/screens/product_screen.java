package com.myapplication.condingexam.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myapplication.condingexam.R;
import com.myapplication.condingexam.adapter.ProductsAdapter;
import com.myapplication.condingexam.adapter.RecommendationAdapter;
import com.myapplication.condingexam.model.Products;
import com.myapplication.condingexam.provider.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class product_screen extends AppCompatActivity {

    RecyclerView all_products;
    RequestQueue requestQueue;
    private List<Products> productsList;
    private ShimmerFrameLayout shimmer_view_all;
    ImageView cart_prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        all_products = findViewById(R.id.all_products);
        shimmer_view_all = findViewById(R.id.shimmer_view_all);
        cart_prod = findViewById(R.id.cart_prod);
        shimmer_view_all.startShimmer();

        all_products.setHasFixedSize(true);
        all_products.setLayoutManager(new GridLayoutManager(product_screen.this, 2));

        requestQueue = VolleySingleton.getmInstance(product_screen.this).getRequestQueue();

        productsList = new ArrayList<>();

        cart_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bs = new BottomSheetDialog(product_screen.this);
                bs.setContentView(R.layout.bottom_sheet_cart);

                Button ca_continue = bs.findViewById(R.id.ca_continue);

                ca_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bs.dismiss();
                    }
                });

                bs.show();
            }
        });
        fetchProducts();
    }
    private void fetchProducts() {
        String $url = "https://sw-coding-challenge.herokuapp.com/api/v1/products";
        String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkNvZGVyIn0.B1QyKzKxzpxay1__A8B85ij32rqFoOIAFGDqBmqXhvs";

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, $url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray dataOBJ = response.getJSONArray("d");
                        for (int k = 0; k < dataOBJ.length(); k++) {
                            JSONObject jsonChild = dataOBJ.getJSONObject(k);
                            JSONArray sizeObj = jsonChild.getJSONArray("sizes");
                            for (int j = 0; j < sizeObj.length(); j++) {
                                Products prod = new Products(jsonChild.getString("originalPrice"), jsonChild.getString("percentOff"),
                                        jsonChild.getString("imageUrl"), jsonChild.getString("name")
                                        , jsonChild.getString("description"), jsonChild.getString("salePrice"));
                                productsList.add(prod);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ProductsAdapter adapter = new ProductsAdapter(product_screen.this, productsList);
                Collections.shuffle(productsList);
                all_products.setAdapter(adapter);
                shimmer_view_all.setVisibility(View.GONE);
                all_products.setVisibility(View.VISIBLE);

                shimmer_view_all.stopShimmer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(product_screen.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + ACCESS_TOKEN);
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}