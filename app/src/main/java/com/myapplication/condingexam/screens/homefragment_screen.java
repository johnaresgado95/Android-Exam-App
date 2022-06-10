package com.myapplication.condingexam.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragment_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragment_screen extends Fragment {

    RecyclerView rv_products, rv_products2;
    RequestQueue requestQueue;
    private List<Products> productsList, productsList2;
    private ShimmerFrameLayout shimmer_products, shimmer_products2;
    TextView seeall_dd, seeall_rr;
    ImageView cart_home;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homefragment_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragment_screen newInstance(String param1, String param2) {
        homefragment_screen fragment = new homefragment_screen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);

        rv_products = view.findViewById(R.id.rv_products);
        rv_products2 = view.findViewById(R.id.rv_products2);
        shimmer_products = view.findViewById(R.id.shimmer_view_support);
        shimmer_products2 = view.findViewById(R.id.shimmer_view_support2);
        seeall_dd = view.findViewById(R.id.seeall_dd);
        seeall_rr = view.findViewById(R.id.seeall_rr);
        cart_home = view.findViewById(R.id.cart_home);

        shimmer_products.startShimmer();
        shimmer_products2.startShimmer();
        rv_products.setHasFixedSize(true);
        rv_products.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rv_products2.setHasFixedSize(true);
        rv_products2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();

        productsList = new ArrayList<>();
        productsList2 = new ArrayList<>();
        fetchProducts();

        seeall_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), product_screen.class));
                ProductsAdapter.itemcount = 20;
            }
        });

        seeall_rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), product_screen.class));
                ProductsAdapter.itemcount = 20;
            }
        });

        cart_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bs = new BottomSheetDialog(getContext());
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
        return view;
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
                                productsList2.add(prod);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ProductsAdapter adapter = new ProductsAdapter(getContext(), productsList);
                Collections.shuffle(productsList);
                rv_products.setAdapter(adapter);
                ProductsAdapter.itemcount = 4;
                shimmer_products.setVisibility(View.GONE);
                rv_products.setVisibility(View.VISIBLE);

                RecommendationAdapter adapterreco = new RecommendationAdapter(getContext(), productsList2);
                Collections.shuffle(productsList2);
                rv_products2.setAdapter(adapterreco);
                shimmer_products2.setVisibility(View.GONE);
                rv_products2.setVisibility(View.VISIBLE);

                shimmer_products.stopShimmer();
                shimmer_products2.stopShimmer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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