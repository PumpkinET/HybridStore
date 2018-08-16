package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.hybridstore.ItemsActivity;
import com.demo.hybridstore.com.hybridstore.adapters.HistoryAdapter;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.Order;
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

public class HistoryFragmentTab extends Fragment {
    private HistoryAdapter mAdapter;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    View rootView;

    public HistoryFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history_tab, container, false);
        new OrderAsyncer().execute();

        mRecycleView = rootView.findViewById(R.id.historyrecycleviewer);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());

        return rootView;

    }

    public class OrderAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/OrderController?session="+Auth.session);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                urlConnection.connect();
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
            Gson gs = new GsonBuilder().create();
            final Order[] order = gs.fromJson(result, Order[].class);

            mAdapter = new HistoryAdapter(new ArrayList<Order>(Arrays.asList(order)));
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(mLayoutManager);

            mAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent i = new Intent(getActivity(), ItemsActivity.class);
                    i.putExtra("shopUrl", order[position].getShopIp());
                    i.putExtra("items", order[position].getItems());
                    startActivity(i);
                }
            });
        }
    }
}