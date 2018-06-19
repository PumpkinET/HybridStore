package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hybridstore.app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HistoryFragmentTab extends Fragment {

    View rootView;

    public HistoryFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history_tab, container, false);


        return rootView;

    }


}