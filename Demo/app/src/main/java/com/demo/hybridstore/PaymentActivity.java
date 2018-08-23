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

import java.io.BufferedOutputStream;
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
        final float price = b.getFloat("finalPrice");
        final String shopName = b.getString("shopName");
        eShopName.setText(shopName);
        totalPrice.setText(price + "₪");

        for(int i = 0; i<CartActivity.card.size(); i++) {
            items += CartActivity.card.get(i).getId()+ ",";
        }
        FloatingActionButton pay = findViewById(R.id.payment_Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(validate(firstname, lasttime, streetadd, country, city, postalCode) == true)
                        new OrderAsyncer().execute(
                                email.getText().toString(),
                                shopName,
                                firstname.getText().toString(),
                                lasttime.getText().toString(),
                                streetadd.getText().toString(),
                                country.getText().toString(),
                                city.getText().toString(),
                                postalCode.getText().toString(),
                                price+"",
                                items
                        );
            }
        });
    }
    public boolean validate(EditText firstName, EditText lastName, EditText streetAdd, EditText country, EditText city, EditText postalCode) {
        boolean res = true;
        if(firstName.getText().toString().length() == 0) {
            firstName.setError("First name must be filled out");
            firstName.requestFocus();
            res = false;
        }
        if(lastName.getText().toString().length() == 0) {
            lastName.setError("Last name must be filled out");
            lastName.requestFocus();
            res = false;
        }
        if(streetAdd.getText().toString().length() == 0) {
            streetAdd.setError("Street Address must be filled out");
            streetAdd.requestFocus();
            res = false;
        }
        if(country.getText().toString().length() == 0) {
            country.setError("Country must be filled out");
            country.requestFocus();
            res = false;
        }
        if(city.getText().toString().length() == 0) {
            city.setError("City must be filled out");
            city.requestFocus();
            res = false;
        }
        if(postalCode.getText().toString().length() == 0) {
            postalCode.setError("Postal code must be filled out");
            postalCode.requestFocus();
            res = false;
        }
        return res;
    }
    public class OrderAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/OrderController?session="+Auth.session);
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
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setFixedLengthStreamingMode(jObj.toString().getBytes().length);
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(jObj.toString());
                writer.flush();
                writer.close();
                out.close();
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
