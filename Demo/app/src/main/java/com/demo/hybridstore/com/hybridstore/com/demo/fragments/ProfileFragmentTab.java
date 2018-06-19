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

public class ProfileFragmentTab extends Fragment {

    ImageView img;
    EditText username, password, email;
    Button update;
    View rootView;

    public ProfileFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        img = (ImageView) rootView.findViewById(R.id.updateProfileAvatar);
        username = (EditText) rootView.findViewById(R.id.updateProfileUsername);
        password = (EditText) rootView.findViewById(R.id.updateProfilePassword);
        email = (EditText) rootView.findViewById(R.id.updateProfileEmail);
        update = (Button) rootView.findViewById(R.id.updateProfileButton);

        Picasso.get().load(Auth.avatar).into(img);
        username.setText(Auth.username);
        password.setText(Auth.password);
        email.setText(Auth.email);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProfileAsyncer().execute(username.getText().toString(), password.getText().toString(), email.getText().toString());
            }
        });

        return rootView;

    }

    public class ProfileAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/AuthController");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                JSONObject jObj = new JSONObject();
                jObj.put("username", params[0]);
                jObj.put("password", params[1]);

                OutputStream os = urlConnection.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jObj.toString());
                writer.flush();
                writer.close();

                urlConnection.connect();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == 200) {
                    InputStream stream = urlConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    return reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                Gson gs = new GsonBuilder().create();
                Login login = gs.fromJson(result, Login.class);
            }
        }
    }

}