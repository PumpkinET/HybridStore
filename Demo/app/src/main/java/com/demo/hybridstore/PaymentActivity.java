package com.demo.hybridstore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.ErrorMessage;
import com.demo.hybridstore.com.hybridstore.model.Shop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hybridstore.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class PaymentActivity extends AppCompatActivity {
    public String items = "";
    float totalPriceValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setTitle("Confirm Payment");

        //calculate final price
        for (int i = 0; i < CartActivity.card.size(); i++)
            totalPriceValue += CartActivity.card.get(i).getPrice() * CartActivity.card.get(i).getQuantity();
        //Initialize view ids
        final EditText eShopName = findViewById(R.id.payment_shopName);
        final EditText totalPrice = findViewById(R.id.payment_totalPrice);
        final EditText email = findViewById(R.id.payment_email);

        final EditText fullname = findViewById(R.id.payment_fullName);
        final EditText streetadd = findViewById(R.id.payment_streetAddress);
        final EditText country = findViewById(R.id.payment_country);
        final EditText city = findViewById(R.id.payment_city);
        final EditText postalCode = findViewById(R.id.payment_postalCode);
        final EditText phoneNumber = findViewById(R.id.payment_phoneNumber);

        email.setText(Auth.email);
        Bundle b = getIntent().getExtras();
        final String shopName = b.getString("shopName");
        eShopName.setText(shopName);
        totalPrice.setText(totalPriceValue + "₪");

        for (int i = 0; i < CartActivity.card.size(); i++) {
            items += CartActivity.card.get(i).getId() + "x" + CartActivity.card.get(i).getQuantity() + ",";
        }
        //Send order to validation
        FloatingActionButton pay = findViewById(R.id.payment_Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(fullname, streetadd, country, city, postalCode, phoneNumber) == true) {
                    new OrderAsyncer().execute(
                            email.getText().toString(),
                            shopName,
                            fullname.getText().toString(),
                            streetadd.getText().toString(),
                            country.getText().toString(),
                            city.getText().toString(),
                            postalCode.getText().toString(),
                            totalPriceValue + "",
                            items,
                            phoneNumber.getText().toString()
                    );
                }
            }
        });

        if (Auth.fullname != null) {
            fullname.setText(Auth.fullname);
        }
        if (Auth.streetAdd != null) {
            streetadd.setText(Auth.streetAdd);
        }
        if (Auth.country != null) {
            country.setText(Auth.country);
        }
        if (Auth.city != null) {
            city.setText(Auth.city);
        }
        if (Auth.postalCode != null) {
            postalCode.setText(Auth.postalCode);
        }
        if (Auth.phonenumber != null) {
            phoneNumber.setText(Auth.phonenumber);
        }
    }

    //Validate each value in input boxes in order details
    public boolean validate(EditText fullname, EditText streetAdd, EditText country, EditText city, EditText postalCode, EditText phoneNumber) {
        boolean res = true;
        if (fullname.getText().toString().length() == 0) {
            fullname.setError("Full name must be filled out");
            fullname.requestFocus();
            res = false;
        }
        if (streetAdd.getText().toString().length() == 0) {
            streetAdd.setError("Street Address must be filled out");
            streetAdd.requestFocus();
            res = false;
        }
        if (country.getText().toString().length() == 0) {
            country.setError("Country must be filled out");
            country.requestFocus();
            res = false;
        }
        if (city.getText().toString().length() == 0) {
            city.setError("City must be filled out");
            city.requestFocus();
            res = false;
        }
        if (postalCode.getText().toString().length() == 0) {
            postalCode.setError("Postal code must be filled out");
            postalCode.requestFocus();
            res = false;
        }
        if (phoneNumber.getText().toString().length() == 0) {
            phoneNumber.setError("Phone number must be filled out");
            phoneNumber.requestFocus();
            res = false;
        } else if (!(phoneNumber.getText().toString().matches("[0-9]+")) || phoneNumber.getText().toString().length() < 9 || phoneNumber.getText().toString().length() > 10) {
            phoneNumber.setError("Phone number must be at least 9 numbers starting 0X/05X ");
            phoneNumber.requestFocus();
            res = false;
        }
        return res;
    }

    //Send order details to server
    public class OrderAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/OrderController?session=" + Auth.session);
                JSONObject jObj = new JSONObject();
                jObj.put("email", params[0]);
                jObj.put("shopName", params[1]);
                jObj.put("fullName", params[2]);
                jObj.put("streetAdd", params[3]);
                jObj.put("country", params[4]);
                jObj.put("city", params[5]);
                jObj.put("postalCode", params[6]);
                jObj.put("totalPrice", params[7]);
                jObj.put("items", params[8]);
                jObj.put("phoneNumber", params[9]);
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
                if (statusCode == 200) {
                    InputStream stream = urlConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    return reader.readLine();
                } else if (statusCode == 401) {
                    return "401";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Show if the order is succeeded via toast else show the error message via toast as well
        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("401")) {
                    Toast.makeText(getApplicationContext(), "Login first!", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gs = new GsonBuilder().create();
                    ErrorMessage res = gs.fromJson(result, ErrorMessage.class);
                    if (res.isResult() == true) {
                        Intent myIntent = new Intent(PaymentActivity.this, ConfirmActivity.class);
                        PaymentActivity.this.startActivity(myIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), res.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
