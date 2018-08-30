package com.demo.hybridstore.com.hybridstore.adapters;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Cart;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    private ArrayList<Cart> mCardList;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mListener2;

    public CartAdapter(ArrayList<Cart> cardList) {
        mCardList = cardList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mListener2 = listener;
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mImageView;
        public TextView mPrice;
        public TextView mQuantity;
        public FloatingActionButton mDec;
        public FloatingActionButton mInc;
        public CardViewHolder(View itemView, final OnItemClickListener listener, final OnItemLongClickListener listener2) {
            super(itemView);

            //initialize view ids
            mTitle = itemView.findViewById(R.id.cart_Title);
            mImageView = itemView.findViewById(R.id.cart_Thumbnail);
            mPrice = itemView.findViewById(R.id.cart_Price);
            mQuantity = itemView.findViewById(R.id.cart_quantity);
            mDec = itemView.findViewById(R.id.cart_dec);
            mInc = itemView.findViewById(R.id.cart_inc);

            //on long click listener
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener2.onItemLongClick(position);//current position
                        }
                    }
                    return true;
                }
            });

            //on click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);//current position
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        CardViewHolder cm = new CardViewHolder(v, mListener, mListener2);
        return cm;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        final Cart currentItem = mCardList.get(position);

        //on click listener to decrease quantity
        holder.mDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentItem.getQuantity()-1 >= 1) {
                    currentItem.setQuantity(currentItem.getQuantity() - 1);
                    holder.mQuantity.setText(currentItem.getQuantity() + "");
                }
            }
        });

        //on click listener to increase quantity
        holder.mInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem.setQuantity(currentItem.getQuantity()+1);
                holder.mQuantity.setText(currentItem.getQuantity() + "");
            }
        });

        //set data to view
        holder.mTitle.setText(currentItem.getTitle());
        Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView);
        holder.mPrice.setText(currentItem.getPrice() + "₪");
        holder.mQuantity.setText(currentItem.getQuantity() + "");
    }
}
