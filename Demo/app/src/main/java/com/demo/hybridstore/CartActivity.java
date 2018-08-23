package com.demo.hybridstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.demo.hybridstore.com.hybridstore.adapters.CartAdapter;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.TargetShopFragment;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Cart;
import com.hybridstore.app.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Cart> card = new ArrayList<>();
    public static CartAdapter mAdapter = new CartAdapter(card);

    public static String shopName = "";
    FloatingActionButton fab, clear;
    float totalPriceValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("My Cart");
        for (int i = 0; i < card.size(); i++) totalPriceValue += card.get(i).getPrice();

        mRecycleView = findViewById(R.id.cartrecycleviewer);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getBaseContext());

        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter.setOnItemLongClickListener(new CartAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                totalPriceValue -= card.get(position).getPrice();
                removeFromCart(card.get(position));
                //card.remove(position);
                //mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        fab = findViewById(R.id.confirmCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Auth.email != null) {
                    Intent i = new Intent(CartActivity.this, PaymentActivity.class);
                    i.putExtra("finalPrice", totalPriceValue);
                    i.putExtra("shopName", shopName);
                    startActivity(i);
                } else
                    Toast.makeText(CartActivity.this, "Please login first", Toast.LENGTH_LONG).show();
            }
        });
        clear = findViewById(R.id.clearCart);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.clearCart();
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
            card.add(new Cart(cart.getId(), cart.getTitle(), cart.getImageResource(), cart.getPrice()));
            mAdapter.notifyDataSetChanged();
        }
    }

    public static void removeFromCart(Cart cart) {
        for (int i = 0; i < TargetShopFragment.card.size(); i++) {
            if (TargetShopFragment.card.get(i).getId() == cart.getId()) {
                TargetShopFragment.card.get(i).resetColor();
            }
        }
        TargetShopFragment.mAdapter.notifyDataSetChanged();
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getTitle().equals(cart.getTitle())) {
                card.remove(i);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public static void clearCart() {
        card.clear();
        mAdapter.notifyDataSetChanged();
    }
}
