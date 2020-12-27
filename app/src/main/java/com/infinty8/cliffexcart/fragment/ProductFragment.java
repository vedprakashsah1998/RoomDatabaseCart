package com.infinty8.cliffexcart.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infinty8.cliffexcart.Adapter.CartAdapter;
import com.infinty8.cliffexcart.Adapter.ProductAdapter;
import com.infinty8.cliffexcart.model.ProductModel;
import com.infinty8.cliffexcart.databinding.FragmentProductBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    private final String Url = "https://next.json-generator.com/api/json/get/4kBVim32Y";

    FragmentProductBinding binding;
    private List<ProductModel> list;
    ProductAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);
        LoadData();
        return binding.getRoot();

    }

    public void LoadData() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object=jsonArray.getJSONObject(i);
                        String name=object.getString("name");
                        int price=object.getInt("price");
                        String desc=object.getString("desc");
                        String image=object.getString("image");
                        ProductModel model=new ProductModel(name,price,desc,image);
                        list.add(model);

                    }


                    binding.productRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new ProductAdapter(getContext(),list);
                    binding.productRecylerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}