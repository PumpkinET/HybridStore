package com.demo.hybridstore.com.hybridstore.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Order;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        public TextView mshippingStatus;
        public RelativeLayout mSelected;
        public CardViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mSelected = itemView.findViewById(R.id.itemSwitcher);
            mTitle = itemView.findViewById(R.id.cardTitle);
            mImageView = itemView.findViewById(R.id.historyImage);
            mPrice = itemView.findViewById(R.id.cardTitle2);

            mStreetAdd = itemView.findViewById(R.id.card_streetAdd);
            mCountry = itemView.findViewById(R.id.card_country);
            mCity = itemView.findViewById(R.id.card_city);
            mPostalCode = itemView.findViewById(R.id.card_postalcode);
            mshippingStatus = itemView.findViewById(R.id.card_shippingStatus);

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

        if(currentItem.getStatus() == 0 ) {
            holder.mSelected.setBackgroundColor(Color.parseColor("#e74c3c"));
            holder.mshippingStatus.setText("Your order has been received");
        }
        else if(currentItem.getStatus() == 1 ) {
            holder.mSelected.setBackgroundColor(Color.parseColor("#f1c40f"));
            holder.mshippingStatus.setText("Your order has been shipped");
        }
        else {
            holder.mSelected.setBackgroundColor(Color.parseColor("#2ecc71"));
            holder.mshippingStatus.setText("Your order has been delivered");
        }
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
