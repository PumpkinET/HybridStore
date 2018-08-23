package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.hybridstore.CartActivity;
import com.demo.hybridstore.com.hybridstore.model.Cart;
import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.adapters.ItemAdapter;
import com.demo.hybridstore.com.hybridstore.model.Item;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class TargetShopFragment extends Fragment {

    private RecyclerView mRecycleView;
    public static ItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mViewCard;
    View rootView;
    Shop shop;
    public static ArrayList<Item> card;
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

        if (CartActivity.card.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to start new cart?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CartActivity.clearCart();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else
            Toast.makeText(getContext(), "Long press on item to add to cart!", Toast.LENGTH_SHORT).show();

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
            Item[] item = gs.fromJson(result, Item[].class);

            mViewCard = rootView.findViewById(R.id.viewCard);
            mRecycleView = rootView.findViewById(R.id.recycleviewer);
            mRecycleView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(rootView.getContext());
            TargetShopFragment.card = new ArrayList<Item>(Arrays.asList(item));

            for (int i = 0; i < card.size(); i++) {
                card.get(i).resetColor();
            }

            mAdapter = new ItemAdapter(card);
            mRecycleView.setAdapter(mAdapter);
            mRecycleView.setLayoutManager(mLayoutManager);

            mAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    TargetItemFragment fragment = new TargetItemFragment();
                    fragment.setTargetItem(card.get(position), shop.getShopName());
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            ((FragmentActivity) rootView.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            mAdapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
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