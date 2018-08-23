package com.demo.hybridstore.com.hybridstore.adapters;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.adapter_shop, null);
        }
        final Shop shop = filList.get(position);

        //initialize view ids
        final ImageView mShopThumbnail = (ImageView) convertView.findViewById(R.id.shop_Thumbnail);
        final TextView mShopSubtitles = (TextView) convertView.findViewById(R.id.shop_Subtitles);
        final ImageButton mShopInfo = (ImageButton) convertView.findViewById(R.id.shop_Information);
        final ImageButton mShopHome = (ImageButton) convertView.findViewById(R.id.shop_Home);
        final ImageButton mShopLocation = (ImageButton) convertView.findViewById(R.id.shop_Location);
        final ImageButton mShopPhoneNumber = (ImageButton) convertView.findViewById(R.id.shop_PhoneNumber);

        //set data to view
        mShopSubtitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());
        Picasso.get().load(shop.getShopThumbnail()).into(mShopThumbnail);

        mShopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShopSubtitles.setText(shop.getShopName() + "\n" + shop.getShopDescription());
            }
        });

        //open target shop
        mShopHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TargetShopFragment fragment = new TargetShopFragment();
                fragment.setTargetShop(shop);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //open google maps
        //open waze
        mShopLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=32.705933,35.325703");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                v.getContext().startActivity(mapIntent);
                try
                {
                    String url = "https://waze.com/ul?q="+shop.getShopAddress();
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                    v.getContext().startActivity( intent );
                }
                catch ( ActivityNotFoundException ex  )
                {
                    // If Waze is not installed, open it in Google Play:
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                    v.getContext().startActivity(intent);
                }
            }
        });

        //open call activity
        mShopPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+shop.getShopPhone()));
                v.getContext().startActivity(callIntent);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        //filter results (search)
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String searchStr = constraint.toString().toUpperCase();
                ArrayList<Shop> resultsData = new ArrayList<Shop>();
                for (int i = 0; i < orgList.size(); i++) {
                    if (orgList.get(i).getShopName().toUpperCase().startsWith(searchStr))
                        resultsData.add(orgList.get(i));
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