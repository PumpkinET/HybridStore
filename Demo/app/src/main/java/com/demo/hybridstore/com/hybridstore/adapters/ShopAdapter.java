package com.demo.hybridstore.com.hybridstore.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.TargetShopFragment;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    ArrayList<Shop> orgList;
    ArrayList<Shop> filList;

    public ShopAdapter(Context c, ArrayList<Shop> shops) {
        mContext = c;
        orgList = shops;
        filList = shops;
    }

    public int getCount() {
        return filList.size();
    }

    public Object getItem(int position) {
        return filList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Shop shop = filList.get(position);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String searchStr = constraint.toString().toUpperCase();
                ArrayList<Shop> resultsData = new ArrayList<Shop>();
                for(int i = 0; i<orgList.size(); i++) {
                    if (orgList.get(i).getShopName().toUpperCase().startsWith(searchStr)) resultsData.add(orgList.get(i));
                }
                results.count = resultsData.size();
                results.values = resultsData;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filList = (ArrayList<Shop>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}