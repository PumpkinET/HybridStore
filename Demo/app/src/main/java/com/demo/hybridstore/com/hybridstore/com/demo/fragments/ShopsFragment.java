package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

/**
 * Created by Dell Latitude on 11/05/2018.
 */


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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

public class ShopsFragment extends Fragment {

    View rootView;

    public ShopsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shops, container, false);
        getActivity().setTitle("Shops");

        new ShopAsycner().execute();
        return rootView;
    }


    public class ShopAsycner extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/ShopController");
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
                GridView gridview = (GridView) rootView.findViewById(R.id.shopsGridView);
                gridview.setAdapter(new ShopAdapter(rootView.getContext(), shop));
            }
        }
    }
}