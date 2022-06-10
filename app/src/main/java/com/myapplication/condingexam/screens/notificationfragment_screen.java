package com.myapplication.condingexam.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.myapplication.condingexam.adapter.NotificationsAdapter;
import com.myapplication.condingexam.model.Notification;
import com.myapplication.condingexam.provider.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notificationfragment_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notificationfragment_screen extends Fragment {

    RecyclerView rv_notifs;
    RequestQueue requestQueue;
    private List<Notification> notifList;
    private ShimmerFrameLayout shimmer_view_notif;
    ImageView cart_not;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notificationfragment_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notificationfragment_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static notificationfragment_screen newInstance(String param1, String param2) {
        notificationfragment_screen fragment = new notificationfragment_screen();
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
        View view = inflater.inflate(R.layout.fragment_notificationscreen, container, false);
        rv_notifs = view.findViewById(R.id.rv_notifs);
        shimmer_view_notif = view.findViewById(R.id.shimmer_view_notif);
        cart_not = view.findViewById(R.id.cart_not);
        shimmer_view_notif.startShimmer();
        rv_notifs.setHasFixedSize(true);
        rv_notifs.setLayoutManager(new LinearLayoutManager(getContext()));

        requestQueue = VolleySingleton.getmInstance(getContext()).getRequestQueue();
        notifList = new ArrayList<>();
        fetchNotifs();

        cart_not.setOnClickListener(new View.OnClickListener() {
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

    private void fetchNotifs() {
        String $url = "https://sw-coding-challenge.herokuapp.com/api/v1/notifications";
        String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkNvZGVyIn0.B1QyKzKxzpxay1__A8B85ij32rqFoOIAFGDqBmqXhvs";

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, $url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray dataOBJ = response.getJSONArray("d");
                        for (int k = 0; k < dataOBJ.length(); k++) {
                            JSONObject jsonChild = dataOBJ.getJSONObject(k);
                            Notification prod = new Notification(jsonChild.getString("title"), jsonChild.getString("description"));
                            notifList.add(prod);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NotificationsAdapter adapter= new NotificationsAdapter(getContext(), notifList);
                rv_notifs.setAdapter(adapter);
                shimmer_view_notif.setVisibility(View.GONE);
                rv_notifs.setVisibility(View.VISIBLE);
                shimmer_view_notif.stopShimmer();
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