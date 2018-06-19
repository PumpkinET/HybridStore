package com.demo.hybridstore.com.hybridstore.adapters;

/**
 * Created by Dell Latitude on 16/06/2018.
 */

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.TargetShopFragment;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.squareup.picasso.Picasso;

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private Shop[] shops;

    public ShopAdapter(Context c, Shop[] shops) {
        mContext = c;
        this.shops = shops;
    }

    public int getCount() {
        return shops.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Shop shop = shops[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.adapter_shop, null);
        }
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.shopThumbnail);
        final TextView shopSubTitles = (TextView) convertView.findViewById(R.id.shopSubTitles);
        Picasso.get().load(shop.getShopThumbnail()).into(imageView);
        shopSubTitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());
        ImageButton info = (ImageButton) convertView.findViewById(R.id.shopInformation);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopSubTitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());
            }
        });
        ImageButton home = (ImageButton) convertView.findViewById(R.id.shopHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TargetShopFragment fragment = new TargetShopFragment();
                fragment.setTargetShop(shop);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }

}