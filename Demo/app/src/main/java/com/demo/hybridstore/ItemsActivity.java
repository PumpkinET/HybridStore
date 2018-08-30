package com.demo.hybridstore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.hybridstore.com.hybridstore.adapters.ItemAdapter;
import com.demo.hybridstore.com.hybridstore.model.Item;
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

public class ItemsActivity extends AppCompatActivity {
    private ItemAdapter mAdapter;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        setTitle("Ordered Items");

        //Initialize view ids
        mRecycleView = findViewById(R.id.itemsrecycleviewer);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getBaseContext());

        Bundle b = getIntent().getExtras();
        final String ip = b.getString("shopUrl");
        final String items = b.getString("items");
        new ExpandOrderAsyncer().execute(ip, items);
    }

    public class ExpandOrderAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0] + "&items=" + params[1]);
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
            if (result != null) {
                Gson gs = new GsonBuilder().create();
                final Item[] item = gs.fromJson(result, Item[].class);

                mAdapter = new ItemAdapter(new ArrayList<Item>(Arrays.asList(item)));
                mRecycleView.setAdapter(mAdapter);
                mRecycleView.setLayoutManager(mLayoutManager);
            }
        }
    }
}
