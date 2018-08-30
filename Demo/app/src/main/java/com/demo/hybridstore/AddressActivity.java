package com.demo.hybridstore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.Login;
import com.hybridstore.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddressActivity extends AppCompatActivity {
    EditText fullname;
    EditText streetadd;
    EditText country;
    EditText city;
    EditText postalCode;
    EditText phoneNumber;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        setTitle("Shipping Address");
        //Initialize view ids
        fullname = findViewById(R.id.shippingAddress_fullName);
        streetadd = findViewById(R.id.shippingAddress_streetAddress);
        country = findViewById(R.id.shippingAddress_country);
        city = findViewById(R.id.shippingAddress_city);
        postalCode = findViewById(R.id.shippingAddress_postalCode);
        phoneNumber = findViewById(R.id.shippingAddress_phoneNumber);
        save = findViewById(R.id.shippingAddress_save);

        //Check if shipping address is found in database if so display it accordingly
        if (fullname.getText().toString().isEmpty()) {
            fullname.setText(Auth.fullname);
        }
        if (streetadd.getText().toString().isEmpty()) {
            streetadd.setText(Auth.streetAdd);
        }
        if (country.getText().toString().isEmpty()) {
            country.setText(Auth.country);
        }
        if (city.getText().toString().isEmpty()) {
            city.setText(Auth.city);
        }
        if (postalCode.getText().toString().isEmpty()) {
            postalCode.setText(Auth.postalCode);
        }
        if (phoneNumber.getText().toString().isEmpty()) {
            phoneNumber.setText(Auth.phonenumber);
        }
        //Save the shipping address after clicking the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(fullname, streetadd, country, city, postalCode, phoneNumber) == true)
                    new AddressAsyncer().execute(
                            fullname.getText().toString(),
                            streetadd.getText().toString(),
                            country.getText().toString(),
                            city.getText().toString(),
                            postalCode.getText().toString(),
                            phoneNumber.getText().toString()
                    );
            }
        });
    }
    //Validate each value in input boxes in shipping address
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
        if(phoneNumber.getText().toString().length() == 0) {
            phoneNumber.setError("Phone number must be filled out");
            phoneNumber.requestFocus();
            res = false;
        }
        else if(!(phoneNumber.getText().toString().matches("[0-9]+")) || phoneNumber.getText().toString().length() < 9 || phoneNumber.getText().toString().length() > 10)
        {
            phoneNumber.setError("Phone number must be at least 9 numbers, starting 0X/05X");
            phoneNumber.requestFocus();
            res = false;
        }
        return res;
    }

    public class AddressAsyncer extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
        }
        //Send shipping address to server
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/AddressController?session=" + Auth.session);
                JSONObject jObj = new JSONObject();
                jObj.put("email", Auth.email);
                jObj.put("fullname", params[0]);
                jObj.put("streetAdd", params[1]);
                jObj.put("country", params[2]);
                jObj.put("city", params[3]);
                jObj.put("postalCode", params[4]);
                jObj.put("phonenumber", params[5]);
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
                    return "Succesfully updated";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        //Update the shipping address with the new address if updated
        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Auth.fullname = fullname.getText().toString();
                Auth.streetAdd = streetadd.getText().toString();
                Auth.country = country.getText().toString();
                Auth.city = city.getText().toString();
                Auth.postalCode = postalCode.getText().toString();
                Auth.phonenumber = phoneNumber.getText().toString();
            }
        }
    }
}

