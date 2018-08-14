package com.demo.hybridstore;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    LinearLayout profileLayout;
    ImageView myCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CategoriesFragment fragment = new CategoriesFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(false);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName)).setText("Welcome guest, ");
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileEmail)).setText("Please login to start buying.");
        navigationView.setNavigationItemSelectedListener(this);


        profileLayout = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.profileLayout);

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Auth.email != null) {
                    ProfileFragment fragment = new ProfileFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        myCart = (ImageView)findViewById(R.id.myCart);
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_home) {
//            MainFragment fragment = new MainFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.commit();
//        } else
        if (id == R.id.nav_login) {
            LoginFragment fragment = new LoginFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_signup) {
            SignupFragment fragment = new SignupFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_categories) {
            CategoriesFragment fragment = new CategoriesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_shops) {
            ShopsFragment fragment = new ShopsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_signout) {
            this.resetMenu();
            Toast.makeText(MainActivity.this, "Why did you left us!", Toast.LENGTH_SHORT).show();
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loginMenu(String name, String email, String avatar) {
        navigationView.getMenu().findItem(R.id.nav_signup).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(true);

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName)).setText(name);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileEmail)).setText(email);

        ImageView img = navigationView.getHeaderView(0).findViewById(R.id.profileAvatar);
        img.setBackgroundColor(Color.TRANSPARENT);
        Picasso.get().load(avatar).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(img);

        Toast.makeText(MainActivity.this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();
    }

    public void resetMenu() {
        Auth.avatar = null;
        Auth.email = null;
        Auth.password = null;
        Auth.session = null;
        navigationView.getMenu().findItem(R.id.nav_signup).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(false);

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName)).setText("Welcome guest, ");
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileEmail)).setText("Please login to start buying.");

        ImageView img = navigationView.getHeaderView(0).findViewById(R.id.profileAvatar);
        img.setImageResource(R.drawable.ic_menu_login);
        //img.setBackground(R.drawable.ic_menu_login);
    }
}
