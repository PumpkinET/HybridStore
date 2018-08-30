package com.demo.hybridstore;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.hybridstore.com.hybridstore.com.demo.fragments.CategoriesFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.LoginFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.ProfileFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.ShopsFragment;
import com.demo.hybridstore.com.hybridstore.com.demo.fragments.SignupFragment;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.hybridstore.app.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

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
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

        //initialize view ids
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(false);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName)).setText("Welcome guest, ");
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileEmail)).setText("Please login to start buying.");
        navigationView.setNavigationItemSelectedListener(this);
        myCart = (ImageView) findViewById(R.id.myCart);
        profileLayout = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.profileLayout);

        //profile button
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
        //cart button
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    //alert message when back press on main menu
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_shops) {
            ShopsFragment fragment = new ShopsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_signout) {
            this.resetMenu();
            Toast.makeText(MainActivity.this, "Why did you left us!", Toast.LENGTH_SHORT).show();
            CategoriesFragment fragment = new CategoriesFragment();
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
        Auth.fullname = null;
        Auth.streetAdd = null;
        Auth.country = null;
        Auth.city = null;
        Auth.postalCode = null;
        Auth.phonenumber = null;
        Auth.session = null;

        navigationView.getMenu().findItem(R.id.nav_signup).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_signout).setVisible(false);

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileName)).setText("Welcome guest, ");
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.profileEmail)).setText("Please login to start buying.");

        ImageView img = navigationView.getHeaderView(0).findViewById(R.id.profileAvatar);
        img.setImageResource(R.drawable.ic_menu_login);
    }

    public void updateMenu(int id) {
        navigationView.setCheckedItem(id);
    }

}
