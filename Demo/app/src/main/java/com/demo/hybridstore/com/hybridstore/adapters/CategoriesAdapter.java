package com.demo.hybridstore.com.hybridstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Categories;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

public class CategoriesAdapter extends BaseAdapter {
    private Context mContext;
    private Categories[] categories;

    public CategoriesAdapter(Context c, Categories[] categories) {
        mContext = c;
        this.categories = categories;
    }

    public int getCount() {
        return categories.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Categories category = categories[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.adapter_categories, null);
        }
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.categoryThumbnail);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.categoryName);
        Picasso.get().load(category.getCategoryThumbnail()).into(imageView);
        nameTextView.setText(category.getCategoryName());

        return convertView;
    }
}
