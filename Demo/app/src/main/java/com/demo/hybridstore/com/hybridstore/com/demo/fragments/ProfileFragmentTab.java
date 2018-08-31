package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.hybridstore.AddressActivity;
import com.demo.hybridstore.MainActivity;
import com.demo.hybridstore.com.hybridstore.model.Auth;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.demo.hybridstore.com.hybridstore.model.ErrorMessage;
import com.demo.hybridstore.com.hybridstore.model.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hybridstore.app.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class ProfileFragmentTab extends Fragment {

    ImageView img;
    View rootView;
    String encodedImage;
    JSONObject jsonObject;
    Uri selectedImage;
    EditText email, password;
    Button update, shippingaddress;

    public ProfileFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        //initialize view ids
        img = (ImageView) rootView.findViewById(R.id.updateProfileAvatar);
        email = (EditText) rootView.findViewById(R.id.updateProfileEmail);
        password = (EditText) rootView.findViewById(R.id.updateProfilePassword);
        shippingaddress = (Button) rootView.findViewById(R.id.updateProfileShippingAddress);
        update = (Button) rootView.findViewById(R.id.updateProfileButton);

        Picasso.get()
                .load(Auth.avatar)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(img);

        email.setText(Auth.email);
        password.setText(Auth.password);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(password) == true)
                    new ProfileAsyncer().execute(password.getText().toString());
            }
        });
        shippingaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }

    public boolean validate(EditText password) {
        boolean res = true;
        if (password.getText().toString().length() == 0) {
            password.setError("Password must be filled out");
            password.requestFocus();
            res = false;
        }
        return res;
    }

    //open gallery activity to select photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                selectedImage = data.getData();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                img.getLayoutParams().width = 300;
                img.getLayoutParams().height = 300;
                img.setImageURI(selectedImage);
            }
        }
    }

    /**
     * this function is used to update profile
     * status 200 : update success
     * status 401 : unauthorized user
     * status 0 : offline server
     */
    public class ProfileAsyncer extends AsyncTask<String, String, String> {
        public void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try {
                jsonObject = new JSONObject();
                jsonObject.put("email", Auth.email);
                jsonObject.put("password", params[0]);
                jsonObject.put("imageString", encodedImage);

                String data = jsonObject.toString();
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/SignupController?session=" + Auth.session);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("PUT");
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                out.close();
                connection.connect();

                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    InputStream stream = connection.getInputStream();
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

        @Override
        public void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("401")) {
                    Toast.makeText(getActivity(), "Login first!", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gs = new GsonBuilder().create();
                    ErrorMessage res = gs.fromJson(result, ErrorMessage.class);
                    if (res.isResult() == true) {
                        Toast.makeText(getActivity(), res.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        //reset cart
                        //erja3 la bara
                    } else {
                        Toast.makeText(getActivity(), res.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}