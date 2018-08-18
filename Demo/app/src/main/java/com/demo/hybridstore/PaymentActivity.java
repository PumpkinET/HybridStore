package com.demo.hybridstore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.hybridstore.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class PaymentActivity extends AppCompatActivity {
    public String items = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setTitle("Confirm Payment");

        final EditText eShopName = findViewById(R.id.payment_shopName);
        final EditText totalPrice = findViewById(R.id.payment_totalPrice);
        final EditText email = findViewById(R.id.payment_email);

        final EditText firstname = findViewById(R.id.payment_firstName);
        final EditText lasttime = findViewById(R.id.payment_lastName);
        final EditText streetadd = findViewById(R.id.payment_streetAddress);
        final EditText country = findViewById(R.id.payment_Country);
        final EditText city = findViewById(R.id.payment_City);
        final EditText postalCode = findViewById(R.id.payment_postalCode);

        email.setText(Auth.email);
        Bundle b = getIntent().getExtras();
        final float id = b.getFloat("finalPrice");
        final String shopName = b.getString("shopName");
        eShopName.setText(shopName + "₪");
        totalPrice.setText(id + "");

        for(int i = 0; i<CartActivity.card.size(); i++) {
            items += CartActivity.card.get(i).getId()+ ",";
        }
        FloatingActionButton pay = findViewById(R.id.payment_Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new OrderAsyncer().execute(
                            email.getText().toString(),
                            shopName,
                            firstname.getText().toString(),
                            lasttime.getText().toString(),
                            streetadd.getText().toString(),
                            country.getText().toString(),
                            city.getText().toString(),
                            postalCode.getText().toString(),
                            id+"",
                            items
                    );
            }
        });


    }

    public class OrderAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/OrderController");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                JSONObject jObj = new JSONObject();
                jObj.put("email",        params[0]);
                jObj.put("shopName",     params[1]);
                jObj.put("firstName",    params[2]);
                jObj.put("lastName",     params[3]);
                jObj.put("streetAdd",    params[4]);
                jObj.put("country",      params[5]);
                jObj.put("city",         params[6]);
                jObj.put("postalCode",   params[7]);
                jObj.put("totalPrice",   params[8]);
                jObj.put("items",        params[9]);
                OutputStream os = urlConnection.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jObj.toString());
                writer.flush();
                writer.close();

                urlConnection.connect();
                int statusCode = urlConnection.getResponseCode();
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
            }
        }
    }
}
