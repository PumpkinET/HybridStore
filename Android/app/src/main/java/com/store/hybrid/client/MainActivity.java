package com.store.hybrid.client;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView list;
    List<Users> temp;
    ArrayAdapter<Users> adapter;
    EditText username;
    EditText password;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = new ArrayList<Users>();
        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<Users>(getApplicationContext(), android.R.layout.simple_spinner_item, temp);
        list.setAdapter(adapter);

        error = (TextView)findViewById(R.id.textViewError);
    }
    public void login(View view) {
        username = (EditText)findViewById(R.id.editTextName);
        password = (EditText)findViewById(R.id.editTextPassword);

        new LoginAsyncTask().execute(username.getText().toString(), password.getText().toString());
    }
    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            BufferedReader buffer = null;
            try
            {
                URL url = new URL("http://192.168.14.140:8080/Server/UsersController");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();

                if (statusCode ==  200) {
                    InputStream stream = urlConnection.getInputStream();
                    buffer = new BufferedReader(new InputStreamReader(stream));
                }
            }
            catch (Exception e) {
            }

            try {
                return buffer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            Gson gs = new GsonBuilder().create();
            temp = Arrays.asList(gs.fromJson(result, Users[].class));
            adapter.addAll(temp);
        }
    }

    private class LoginAsyncTask extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
            error.setText("");
        }
        @Override
        protected Integer doInBackground(String... params) {
            try
            {
                URL url = new URL("http://192.168.14.140:8080/Server/UsersController");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();

                return statusCode;
            }
            catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(Integer result) {
            Log.d("statusCode", result.toString());
            if(result == 200) new MyAsyncTask().execute();
            else error.setText("Wrong username/password!");
        }
    }
}
