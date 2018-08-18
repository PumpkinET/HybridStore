package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.CartActivity;
import com.demo.hybridstore.com.hybridstore.model.Cart;
import com.demo.hybridstore.com.hybridstore.model.Item;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

public class TargetItemFragment extends Fragment {

    View rootView;
    TextView mTitle, mDescription, mPrice;
    ImageView mImageView;
    FloatingActionButton mAddRemove;
    Item item;
    String shopName;
    boolean bAdd = true;
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

        mTitle = (TextView) rootView.findViewById(R.id.history_Title);
        mImageView = (ImageView) rootView.findViewById(R.id.history_Thumbnail);
        mDescription = (TextView) rootView.findViewById(R.id.cart_Description);
        mPrice = (TextView) rootView.findViewById(R.id.cardPrice);
        mAddRemove = (FloatingActionButton) rootView.findViewById(R.id.addRemove);

        for (int i = 0; i < CartActivity.card.size(); i++) {
            if (CartActivity.card.get(i).getId().equals(item.getId())) {
                mAddRemove.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_remove_from_cart));
                bAdd = false;
            }
        }
        mAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bAdd) {
                    CartActivity.addtoCart(new Cart(item.getId(), item.getTitle(), item.getImageResource(), item.getPrice()));
                    mAddRemove.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_remove_from_cart));
                    bAdd = false;
                } else {
                    CartActivity.removeFromCart(new Cart(item.getId(), item.getTitle(), item.getImageResource(), item.getPrice()));
                    mAddRemove.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_add_to_cart));
                    bAdd = true;
                }
            }
        });

        mTitle.setText(item.getTitle());
        Picasso.get().load(item.getImageResource()).into(mImageView);
        mDescription.setText(item.getDescription());
        mPrice.setText("Price " + item.getPrice() + "₪");
        return rootView;
    }
}