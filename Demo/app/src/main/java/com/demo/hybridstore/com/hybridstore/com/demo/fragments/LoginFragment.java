package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.hybridstore.MainActivity;
import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


public class LoginFragment extends Fragment {

    View rootView;
    EditText username, password;
    Button bLogin;
    TextView txtSignup;


    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");
        ((MainActivity) getActivity()).updateMenu(R.id.nav_login);

        username = (EditText) rootView.findViewById(R.id.editText_username);
        password = (EditText) rootView.findViewById(R.id.editView_password);
        bLogin = (Button) rootView.findViewById(R.id.button_login);
        txtSignup = (TextView) rootView.findViewById(R.id.textView_SignUp);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncer().execute(username.getText().toString(), password.getText().toString());
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment fragment = new SignupFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

    public class LoginAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            password.setText("");
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
                jObj.put("email", params[0]);
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
                } else if(statusCode == 403) {
                    return  null;
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                Gson gs = new GsonBuilder().create();
                Login login = gs.fromJson(result, Login.class);

                Auth.email = login.getEmail();
                Auth.password = login.getPassword();
                Auth.avatar = login.getAvatar();
                Auth.name = login.getName();
                Auth.session = login.getSession();

                Log.d("session", login.getSession());
                ((MainActivity) getActivity()).loginMenu(login.getName(), login.getEmail(), login.getAvatar());
                CategoriesFragment fragment = new CategoriesFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
            else Toast.makeText(getActivity(), "Wrong email / password!", Toast.LENGTH_SHORT).show();
        }
    }
}