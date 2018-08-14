package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Item;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

public class TargetItemFragment extends Fragment {

    View rootView;
    TextView mTitle, mDescription, mPrice;
    ImageView mImageView;
    Item item;
    String shopName;
    public TargetItemFragment() {
    }

    public void setTargetItem(Item item, String shopName) {
        this.item = item;
        this.shopName = shopName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_targetitem, container, false);
        getActivity().setTitle(shopName);

        mTitle = (TextView)rootView.findViewById(R.id.cardTitle);
        mImageView = (ImageView)rootView.findViewById(R.id.historyImage);
        mDescription = (TextView)rootView.findViewById(R.id.cardDescription);
        mPrice = (TextView)rootView.findViewById(R.id.cardPrice);

        mTitle.setText(item.getTitle());
        Picasso.get().load(item.getImageResource()).into(mImageView);
        mDescription.setText(item.getDescription());
        mPrice.setText(item.getPrice());
        return rootView;
    }
}