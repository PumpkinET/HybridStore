package com.demo.hybridstore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.hybridstore.com.hybridstore.com.demo.fragments.CategoriesFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.LoginFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.MainFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.ProfileFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.ShopsFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.SignupFragment;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.hybridstore.app.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("My Cart");
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_cart));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
