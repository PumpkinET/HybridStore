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

import com.demo.hybridstore.MainActivity;
import com.demo.hybridstore.com.hybridstore.model.Config;
import com.hybridstore.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment {

    ImageView img;
    View rootView;
    String encodedImage;
    JSONObject jsonObject;
    Uri selectedImage;
    EditText email, password, name;
    Button bSignup;
    public SignupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        getActivity().setTitle("Signup");
        ((MainActivity) getActivity()).updateMenu(R.id.nav_signup);
        img = (ImageView) rootView.findViewById(R.id.imageView_Avatar);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        email = (EditText) rootView.findViewById(R.id.signup_Email);
        password = (EditText) rootView.findViewById(R.id.signup_Password);
        name = (EditText) rootView.findViewById(R.id.signup_Name);
        bSignup = (Button) rootView.findViewById(R.id.bRegister);


        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterAsyncer().execute(email.getText().toString(), password.getText().toString(), name.getText().toString());
            }
        });

        return rootView;
    }

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

    public class RegisterAsyncer extends AsyncTask<String, String, String> {
        public void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("email", params[0]);
                jsonObject.put("name", params[1]);
                jsonObject.put("password", params[2]);
                jsonObject.put("imageString", encodedImage);

                String data = jsonObject.toString();
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/SignupController");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
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
                    return "Succesfully created";
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
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    }
}