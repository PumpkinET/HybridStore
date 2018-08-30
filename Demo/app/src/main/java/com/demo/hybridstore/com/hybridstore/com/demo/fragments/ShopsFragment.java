package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.demo.hybridstore.MainActivity;
import com.demo.hybridstore.com.hybridstore.adapters.ShopAdapter;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hybridstore.app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopsFragment extends Fragment {

    View rootView;
    GridView gridview;
    EditText filter_shops;
    ShopAdapter shopsAdapter;
    String category;

    public ShopsFragment() {
    }

    public void setTargetCategory(String category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shops, container, false);

        //update side menu and title
        getActivity().setTitle("Shops");
        ((MainActivity) getActivity()).updateMenu(R.id.nav_shops);

        new ShopAsycner().execute();

        //initialize view ids
        gridview = (GridView) rootView.findViewById(R.id.shopsGridView);
        filter_shops = (EditText) rootView.findViewById(R.id.filter_shops);

        filter_shops.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                shopsAdapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
    }

    /**
     * this function is used to shops
     * status 200 : get success
     * status 0 : offline server
     */
    public class ShopAsycner extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url;
                if (category == null)
                    url = new URL("http://" + Config.ip + ":8080/appBackend/ShopController");
                else
                    url = new URL("http://" + Config.ip + ":8080/appBackend/ShopController?category=" + category);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200) {
                    InputStream stream = urlConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    return reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                Gson gs = new GsonBuilder().create();
                Shop[] shop = gs.fromJson(result, Shop[].class);

                shopsAdapter = new ShopAdapter(rootView.getContext(), new ArrayList<Shop>(Arrays.asList(shop)));
                gridview.setAdapter(shopsAdapter);
                gridview.setTextFilterEnabled(true);
            }
        }
    }
}