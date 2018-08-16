package com.demo.hybridstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.adapters.CardAdapter;
import com.demo.hybridstore.com.hybridstore.adapters.CartAdapter;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.TargetItemFragment;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Cart;
import com.hybridstore.app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Cart> card = new ArrayList<>();
    public static CartAdapter mAdapter = new CartAdapter(card);
    public static String shopName = "";
    FloatingActionButton fab;
    float totalPriceValue = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("My Cart");
        for (int i = 0; i < card.size(); i++) {
            totalPriceValue += card.get(i).getPrice();
        }
        mRecycleView = findViewById(R.id.cartrecycleviewer);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getBaseContext());

        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter.setOnItemLongClickListener(new CartAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                totalPriceValue -= card.get(position).getPrice();
                card.remove(position);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        fab = findViewById(R.id.confirmCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Auth.email != null) {
                    Intent i = new Intent(CartActivity.this, PaymentActivity.class);
                    i.putExtra("finalPrice", totalPriceValue);
                    i.putExtra("shopName", shopName);
                    startActivity(i);
                }
            }
        });
    }

     public static void addtoCart(Cart cart) {
        boolean b = false;

        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getTitle().equals(cart.getTitle())) {
                b = true;
            }
        }
        if (b == false) {
            card.add(new Cart(cart.getId(),cart.getTitle(), cart.getImageResource(), cart.getPrice()));
            mAdapter.notifyDataSetChanged();
        }
    }
    public static void removeFromCart(Cart cart) {
        for(int i = 0; i<card.size(); i++) {
            if(card.get(i).getTitle().equals(cart.getTitle())) {
                card.remove(i);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
