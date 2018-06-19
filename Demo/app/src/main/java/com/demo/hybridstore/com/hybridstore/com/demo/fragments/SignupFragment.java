package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

/**
 * Created by Dell Latitude on 11/05/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import static android.content.ContentValues.TAG;


public class SignupFragment extends Fragment {

    ImageView img;
    View rootView;
    String encodedImage;
    JSONObject jsonObject;
    JSONObject Response;
    Uri selectedImage;

    public SignupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        getActivity().setTitle("Signup");

        img = (ImageView) rootView.findViewById(R.id.imageView_Avatar);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri selectedImage = data.getData();
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
                new UploadAsyncer().execute(selectedImage);
            }
        }
    }

    public class UploadAsyncer extends AsyncTask<Uri, String, String> {
        public void onPreExecute() {
        }

        private String getRealPathFromURI(Context context, Uri contentUri) {
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } catch (Exception e) {
                Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
                return "";
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        @Override
        protected String doInBackground(Uri... params) {
            try {
                String path = getRealPathFromURI(getContext(), params[0]);
                String filename = path.substring(path.lastIndexOf("/") + 1);
                String file;
                if (filename.indexOf(".") > 0) {
                    file = filename.substring(0, filename.lastIndexOf("."));
                } else {
                    file = filename;
                }
//                Log.d(TAG, "Real Path: " + path);
//                Log.d(TAG, "Filename With Extension: " + filename);
//                Log.d(TAG, "File Without Extension: " + file);
//                Log.d("Vicky", "encodedImage = " + encodedImage);

                jsonObject = new JSONObject();
                jsonObject.put("imageString", encodedImage);
                jsonObject.put("imageName", filename);

                String data = jsonObject.toString();
                URL url = new URL("http://" + Config.ip + ":8080/appBackend/UploadController");
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
        }
    }
}