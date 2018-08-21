package com.demo.hybridstore.com.hybridstore.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.com.demo.fragments.ShopsFragment;
import com.demo.hybridstore.com.hybridstore.model.Categories;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    ArrayList<Categories> orgList;
    ArrayList<Categories> filList;

    public CategoriesAdapter(Context c, ArrayList<Categories> categories) {
        mContext = c;
        orgList = categories;
        filList = categories;
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
            convertView = layoutInflater.inflate(R.layout.adapter_categories, null);
        }
        final Categories category = filList.get(position);

        //initialize view ids
        final ImageView mCategoryThumbnail = (ImageView) convertView.findViewById(R.id.category_Thumbnail);
        final TextView mCategoryTitle = (TextView) convertView.findViewById(R.id.category_Title);

        //set data to view
        Picasso.get().load(category.getCategoryThumbnail()).into(mCategoryThumbnail);
        mCategoryTitle.setText(category.getCategoryName());

        mCategoryThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to target shop
                ShopsFragment fragment = new ShopsFragment();
                fragment.setTargetCategory(category.getCategoryName());
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
        //filter results (search)
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String searchStr = constraint.toString().toUpperCase();
                ArrayList<Categories> resultsData = new ArrayList<Categories>();
                for (int i = 0; i < orgList.size(); i++) {
                    if (orgList.get(i).getCategoryName().toUpperCase().startsWith(searchStr))
                        resultsData.add(orgList.get(i));
                }
                results.count = resultsData.size();
                results.values = resultsData;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filList = (ArrayList<Categories>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
