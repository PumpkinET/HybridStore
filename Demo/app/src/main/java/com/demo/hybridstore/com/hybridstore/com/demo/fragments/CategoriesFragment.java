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
import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesFragment extends Fragment {
    View rootView;
    GridView gridview;
    EditText filter_categories;
    CategoriesAdapter categoriesAdapter;

    public CategoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        //update side menu and title
        getActivity().setTitle("Categories");
        ((MainActivity) getActivity()).updateMenu(R.id.nav_categories);

        //initialize view ids
        gridview = (GridView) rootView.findViewById(R.id.categoriesGridView);
        filter_categories = (EditText) rootView.findViewById(R.id.filter_categories);

        new CategoriesAsyncer().execute();
        //filter categories
        filter_categories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                categoriesAdapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
    }

    /**
     * this function is used to get categories list
     * status 200 : get success
     * status 0 : offline server
     */
    public class CategoriesAsyncer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
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
                categoriesAdapter = new CategoriesAdapter(rootView.getContext(), new ArrayList<Categories>(Arrays.asList(categories)));
                gridview.setAdapter(categoriesAdapter);
                gridview.setTextFilterEnabled(true);
            }
        }
    }
}