package com.demo.hybridstore.com.hybridstore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Order;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CardViewHolder> {
    private ArrayList<Order> mCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mImageView;
        public TextView mPrice;
        public TextView mStreetAdd;
        public TextView mCountry;
        public TextView mCity;
        public TextView mPostalCode;
        public CardViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.cardTitle);
            mImageView = itemView.findViewById(R.id.historyImage);
            mPrice = itemView.findViewById(R.id.cardTitle2);

            mStreetAdd = itemView.findViewById(R.id.card_streetAdd);
            mCountry = itemView.findViewById(R.id.card_country);
            mCity = itemView.findViewById(R.id.card_city);
            mPostalCode = itemView.findViewById(R.id.card_postalcode);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);

                        }
                    }
                }
            });
        }
    }

    public HistoryAdapter(ArrayList<Order> cardList) {
        mCardList = cardList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false);
        CardViewHolder cm = new CardViewHolder(v, mListener);
        return cm;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Order currentItem = mCardList.get(position);

        holder.mTitle.setText(currentItem.getShopName());
        Picasso.get().load(currentItem.getShopThumbnail()).into(holder.mImageView);
        holder.mPrice.setText(currentItem.getFinalPrice());

        holder.mStreetAdd.setText(currentItem.getStreetAdd());
        holder.mCity.setText(currentItem.getCity());
        holder.mCountry.setText(currentItem.getCountry());
        holder.mPostalCode.setText(currentItem.getPostalcode());

    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}
