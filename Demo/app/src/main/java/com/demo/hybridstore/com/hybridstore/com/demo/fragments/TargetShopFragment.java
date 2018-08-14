package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

/**
 * Created by Dell Latitude on 11/05/2018.
 */


import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.CartActivity;
import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.adapters.CardAdapter;
import com.demo.hybridstore.com.hybridstore.model.Item;
import com.demo.hybridstore.com.hybridstore.model.Items;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TargetShopFragment extends Fragment {

    private RecyclerView mRecycleView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mViewCard;
    View rootView;
    Shop shop;

    public TargetShopFragment() {
    }

    public void setTargetShop(Shop shop) {
        this.shop = shop;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_targetshop, container, false);
        getActivity().setTitle(shop.getShopName());
        CartActivity.shopName = shop.getShopName();

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.targetShopThumbnail);
        final TextView shopSubTitles = (TextView) rootView.findViewById(R.id.targetShopSubTitles);
        ImageButton info = (ImageButton) rootView.findViewById(R.id.targetShopInformation);
        ImageButton home = (ImageButton) rootView.findViewById(R.id.targetShopHome);

        Picasso.get().load(shop.getShopThumbnail()).into(imageView);
        shopSubTitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopSubTitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopSubTitles.setText(shop.getShopIp());
            }
        });

        new ItemAsyncer().execute();

        return rootView;
    }

    public class ItemAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL(shop.getShopIp());
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
            Items item = gs.fromJson(result, Items.class);

            mViewCard = rootView.findViewById(R.id.viewCard);
            mRecycleView = rootView.findViewById(R.id.recycleviewer);
            mRecycleView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(rootView.getContext());
            final ArrayList<Item> card = new ArrayList<>();
            for (int i = 0; i < item.getItems().size(); i++) {
                Object[] obj = item.getItems().get(i);
                card.add(new Item(obj[3].toString(), obj[0].toString(), obj[1].toString(), obj[2].toString(), obj[4].toString()));
            }
            mAdapter = new CardAdapter(card);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(mLayoutManager);

            mAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    TargetItemFragment fragment = new TargetItemFragment();
                    fragment.setTargetItem(card.get(position), shop.getShopName());
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            ((FragmentActivity) rootView.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
            });
            mAdapter.setOnItemLongClickListener(new CardAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(int position) {
                    card.get(position).switchColor();
                    mAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
    }
}