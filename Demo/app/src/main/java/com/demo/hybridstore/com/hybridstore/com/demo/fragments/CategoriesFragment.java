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

import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.adapters.CategoriesAdapter;
import com.demo.hybridstore.com.hybridstore.model.Categories;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoriesFragment extends Fragment {

    View rootView;

    public CategoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        getActivity().setTitle("Categories");

        new CategoriesAsyncer().execute();
        return rootView;

    }

    public class CategoriesAsyncer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/CategoriesController");
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
                Categories[] categories = gs.fromJson(result, Categories[].class);
                GridView gridview = (GridView) rootView.findViewById(R.id.categoriesGridView);
                gridview.setAdapter(new CategoriesAdapter(rootView.getContext(), categories));
            }
        }
    }
}